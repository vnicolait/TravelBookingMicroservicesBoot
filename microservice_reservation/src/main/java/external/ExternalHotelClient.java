package external;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import external.dto.FlightResponseDTO;
import external.dto.HotelAvailableRequestDTO;
import external.dto.HotelAvailableResponseDTO;
import external.dto.RoomRentRequestDTO;
import external.dto.RoomRentResponseDTO;
@Service
public class ExternalHotelClient {

	@Autowired
	RestTemplate template;
	private String url="http://localhost:6000/hotels";
	
	public List<HotelAvailableResponseDTO> listAvailableHotels(HotelAvailableRequestDTO dto){

		String jsonResponse = template.getForObject(url+"?location={location}&fecha_inicio={fecha_inicio}&fecha_fin={fecha_inicio}",
				          String.class,dto.getDestination(),dto.getFrom(),dto.getTo());
	  List<HotelAvailableResponseDTO> listHotels=new ArrayList<>();
	     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	ObjectMapper mapper=new ObjectMapper();
	 try {
		 ArrayNode array=(ArrayNode)mapper.readTree(jsonResponse);
	  
		 for(JsonNode node:array) {
			listHotels.add(new HotelAvailableResponseDTO(node.get("idHotel").asInt(),
					                            node.get("name").asText(),
					                            node.get("location").asText(),
					                            node.get("idRoom").asInt(),
					                           new BigDecimal( node.get("priceByNight").asText()),
					                           // Si el valor es null, no lo parsea y asigna null
					                           node.get("checkIn").isNull() ? null : LocalDate.parse(node.get("checkIn").asText(), formatter),
					                           node.get("checkOut").isNull() ? null : LocalDate.parse(node.get("checkOut").asText(), formatter)
					                        		   ));
			                                		   
		}
	 } catch (JsonProcessingException e) {
         e.printStackTrace();
         throw new RuntimeException("Error parsing JSON response");
     }	
	 return listHotels;
    }

	////////////////////////////////////////////////////////////////////
	////////////////CURRENT////////////////////////////////////////
	
	public ResponseEntity<RoomRentResponseDTO> hotelAvailableExternalBlock(RoomRentRequestDTO dto) {
	    HttpEntity<RoomRentRequestDTO> requestEntity = new HttpEntity<>(dto);
	    ResponseEntity<RoomRentResponseDTO> response = template.exchange("http://localhost:6000/hotel", HttpMethod.POST,
	    		requestEntity, RoomRentResponseDTO.class);
		return response;
//		return webClient
//		        .get() //RequestHeadersUriSpec
//		           .uri("http://localhost:8080/block", uri -> uri        
//	                         .queryParam("codeFlight", codeFlight)
//	                         .queryParam("destination", seats)
//	                         .build())
//		        		 //RequestHeadersSpec
//		              .retrieve() //ResponseSpec
//		                .bodyToMono(FlightResponseDTO.class) //Mono<FlightResponseDTO>
//		                  .block(); // FlightResponseDTO
	}
	
}