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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exception.BlockExpiredException;
import com.service.HotelService;
import com.service.OccupationService;

import dto.HotelDTO;
import dto.RoomRentRequestDTO;
import dto.RoomRentResponseDTO;

@RestController
public class HotelController {

	@Autowired
	HotelService service;
	@Autowired 
	OccupationService occupationService;
	
//	@GetMapping(value="hotels",produces=MediaType.APPLICATION_JSON_VALUE)
//	public List<HotelDTO> listaHotelesPorFecha(@RequestParam("location")String location,
//	                       @RequestParam("fecha_inicio")String fecha_inicio, 
//	                         @RequestParam("fecha_fin")String fecha_fin){
//		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//         LocalDate begin = LocalDate.parse(fecha_inicio, formatter);
//         LocalDate end = LocalDate.parse(fecha_fin, formatter);
//         return service.findAvailableHotelsWithRoomsBetweenDates(location, begin, end);
//	}
	
	@GetMapping(value="hotels",produces=MediaType.APPLICATION_JSON_VALUE)
	public List<HotelDTO> listaHotelesPorFecha(@RequestParam("location")String location,
	                       @RequestParam("fecha_inicio")String fecha_inicio, 
	                         @RequestParam("fecha_fin")String fecha_fin){
		    
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
       LocalDate begin = LocalDate.parse(fecha_inicio, formatter);
       LocalDate end = LocalDate.parse(fecha_fin, formatter);
         return service.findAvailableHotelsWithRoomsBetweenDatesSQL(location, begin, end);
	}
	/*   Preferiblemente RequestBody para enviarse en el cuerpo
	 *  los métodos POST suelen usarse para crear recursos, 
	 *  y los detalles de la creación deben enviarse en el cuerpo
	 */
	@PostMapping(value="hotels")
	public ResponseEntity<Void> rentRoomOfHotel(@RequestParam("idHotel")int idHotel,
			                                    @RequestParam("idRoom")int idRoom,
			                                    @RequestParam("startRent")String startRent,
			                                    @RequestParam("endRent")String endRent){
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	       LocalDate begin = LocalDate.parse(startRent, formatter);
	       LocalDate end = LocalDate.parse(endRent, formatter);
	       occupationService.saveRentRoomOccupation(idHotel, idRoom, begin, end);
	       return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value="hotels/{idHotel}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HotelDTO> findByIdHotel(@PathVariable("idHotel")int idHotel){
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	
	@PostMapping(value="hotel")
	public ResponseEntity<RoomRentResponseDTO> rentRoomOfHotelCurrent(@RequestBody RoomRentRequestDTO roomRent){
		RoomRentResponseDTO response=occupationService.rentRoomOfHotelCurrent(roomRent);
	       return new ResponseEntity<RoomRentResponseDTO>(response,HttpStatus.OK);
	}
	
//	@PostMapping(value="hotel/{idOccupation}") better for change state PUT
	@PutMapping(value="ocupaciones/confirmar/{idOccupation}")
    public ResponseEntity<String> confirmReservation(@PathVariable("idOccupation") int idOccupation) {
		occupationService.confirmReservationRoom(idOccupation);
		return ResponseEntity.ok().build();  // Retorna código HTTP 200 OK si todo es correcto
    }
}
