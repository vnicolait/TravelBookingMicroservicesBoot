package external;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import external.dto.FlightRequestDTO;
import external.dto.FlightResponseDTO;

@Service
public class ExternalFlightClient {
	@Autowired
	WebClient webClient;
	
	public List<FlightResponseDTO> listFlightAvailable(FlightRequestDTO dto){
		String jsonResponse = webClient
		          .get()
		         /* .uri(uriBuilder -> uriBuilder
		                  .scheme("http")
		                  .host("localhost")
		                  .port(8080)
		                  .path("/flights")
		          */
		          .uri("http://localhost:8080/flights", uri -> uri        
		                         .queryParam("origin", dto.getOrigin())
		                         .queryParam("destination", dto.getDestination())
		                         .queryParam("startDate", dto.getStartDate())
		                         .queryParam("endDate", dto.getEndDate())
		                         .build())
		          .retrieve()
		          .bodyToMono(String.class)
		          .block();
     List<FlightResponseDTO> flights=new ArrayList<>();
     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		ObjectMapper maper=new ObjectMapper();
	 try {
		ArrayNode array=(ArrayNode)maper.readTree(jsonResponse);
		 for(JsonNode node:array) {
        	flights.add(new FlightResponseDTO(node.get("idFlight").asInt(),
        			                          node.get("origin").asText(),
        			                          node.get("destination").asText(),
        			                          LocalDate.parse(node.get("startDate").asText(), formatter) ,
        			                          LocalDate.parse(node.get("endDate").asText(), formatter),
        			                          new BigDecimal(node.get("price").asText()),
        			                          node.get("availableSeats").asInt()));
        	                                          
         }
     } catch (JsonProcessingException e) {
         e.printStackTrace();
         throw new RuntimeException("Error parsing JSON response");
     }

     return flights;
}

	public FlightResponseDTO flightAvailableExternalBlock(int codeFlight, int seats) {
		return webClient
		        .get() //RequestHeadersUriSpec
		           .uri("http://localhost:8080/block", uri -> uri        
	                         .queryParam("codeFlight", codeFlight)
	                         .queryParam("destination", seats)
	                         .build())
		        		 //RequestHeadersSpec
		              .retrieve() //ResponseSpec
		                .bodyToMono(FlightResponseDTO.class) //Mono<FlightResponseDTO>
		                  .block(); // FlightResponseDTO
	}
	
	public void flightUpdateSeatExternal(int codeFlight, int seat) {
		 webClient
		          .put()
		              .uri("http://localhost:8080/flights",uri -> uri        
		                         .queryParam("idFlight", codeFlight)
		                         .queryParam("seat", seat)
		                  .build())
                            .retrieve()
                              .bodyToMono(Void.class)
                                .block();
				
				
				/*
				.get() //RequestHeadersUriSpec
		           .uri("http://localhost:8080/flights",codeFlight) //RequestHeadersSpec
		              .retrieve() //ResponseSpec
		                .bodyToMono(FlightResponseDTO.class) //Mono<FlightResponseDTO>
		                  .block(); // FlightResponseDTO
	
	*/}
	
}