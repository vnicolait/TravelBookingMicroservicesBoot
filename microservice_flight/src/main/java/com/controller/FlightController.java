package com.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.FlightEntity;
import com.service.ServiceFlight;

import dto.FlightDTO;

@RestController
public class FlightController {
    @Autowired
	ServiceFlight service;
    
    @GetMapping(value="flights",produces=MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<FlightDTO>> listFlight(@RequestParam("origin")String origin,
    		                             @RequestParam("destination") String destination,
    		                             @RequestParam("startDate") String startDate,
    		                             @RequestParam("endDate") String endDate){
    	 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	 // Validar que las fechas no estén vacías
         if (startDate.isEmpty() || endDate.isEmpty()) {
             throw new IllegalArgumentException("Start date or end date cannot be empty");
         }
          LocalDate begin = LocalDate.parse(startDate, formatter);
          LocalDate end = LocalDate.parse(endDate, formatter);
          List<FlightDTO> flights= service.listFlightAvailableBeetwen(origin, destination, begin, end);
        return new ResponseEntity<List<FlightDTO>>(flights,HttpStatus.OK);
    }
    
	@PutMapping(value="flights")
    ResponseEntity<Void> updateSeatFlight(@RequestParam("idFlight")int idFlight,
            @RequestParam("seat") int seat){
    	service.updateSeatFlight(idFlight, seat);
    	return new ResponseEntity<Void>(HttpStatus.OK);
}
	
	 @GetMapping(value="flights-direct",produces=MediaType.APPLICATION_JSON_VALUE)
	    ResponseEntity<List<FlightEntity>> listFlightDirect(@RequestParam("origin")String origin,
	    		                             @RequestParam("destination") String destination,
	    		                             @RequestParam("startDate") String startDate,
	    		                             @RequestParam("endDate") String endDate){
	    	 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	          LocalDateTime begin = LocalDateTime.parse(startDate, formatter);
	          LocalDateTime end = LocalDateTime.parse(endDate, formatter);
	          List<FlightEntity> flights= service.listFlightDirect(origin, destination, begin, end);
	        return new ResponseEntity<List<FlightEntity>>(flights,HttpStatus.OK);
	    }
	 
    @GetMapping(value="flights/{idFlight}",produces=MediaType.APPLICATION_JSON_VALUE)
	 ResponseEntity<FlightDTO> flightById(@PathVariable("idFlight")int idFlight){
		    FlightDTO flight = service.findByFlight(idFlight);
          return  flight!=null?
				 new ResponseEntity<>(flight,HttpStatus.OK):new ResponseEntity<>(HttpStatus.NOT_FOUND);
	 }
    
    /**
     * Endpoint para bloquear asientos en un vuelo.
     */
    @PutMapping("/block")
    public ResponseEntity<Void> blockSeats(@RequestParam("idFlight") int idFlight,
                                           @RequestParam("seats") int seats) {
        boolean success = service.blockSeats(idFlight, seats); // Bloqueo de 10 minutos
        return success ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
