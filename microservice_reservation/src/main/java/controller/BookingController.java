package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import booking.dto.BookingDTO;
import booking.dto.BookingRequestFlightDTO;
import booking.dto.BookingRequestHotelDTO;
import exceptions.BlockExpiredException;
import external.dto.FlightRequestDTO;
import external.dto.FlightResponseDTO;
import external.dto.HotelAvailableRequestDTO;
import external.dto.HotelAvailableResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import service.BookingService;

@CrossOrigin(origins = "*") // Permite peticiones de cualquier origen
@RestController
public class BookingController {

	@Autowired
	BookingService service;
	//Flight
	@GetMapping(value="/flights/available",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FlightResponseDTO>> listAvailableFlight(@RequestBody FlightRequestDTO dto){
		return new ResponseEntity<List<FlightResponseDTO>> (service.listFlightResponse(dto),HttpStatus.OK);
	}

	@PostMapping(value="/flights/bookings",consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookingDTO> createBookingFlight(@RequestBody BookingRequestFlightDTO bookingRequestDTO){
		BookingDTO dtoResponse= service.createBookingForFlight(bookingRequestDTO);
		return dtoResponse!=null?new ResponseEntity<>(dtoResponse,HttpStatus.OK):new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	//Hotel
//	@GetMapping(value="/hotels/available",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<List<HotelAvailableResponseDTO>> listAvailableHotel(@RequestBody HotelAvailableRequestDTO dto){
//		System.out.println(dto.getFrom()+"\n");
//		System.out.println(dto.getTo());
//
//		return new ResponseEntity<List<HotelAvailableResponseDTO>> (service.listHotelResponse(dto),HttpStatus.OK);
//	}
	@GetMapping(value="/hotels/available",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HotelAvailableResponseDTO>> listAvailableHotel(@RequestParam("destination") String destination,
	        @RequestParam("from") String from,
	        @RequestParam("to") String to){
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	       LocalDate begin = LocalDate.parse(from, formatter);
	       System.out.println("micro hotel controller "+ begin);
	       LocalDate end = LocalDate.parse(to, formatter);
	    HotelAvailableRequestDTO dto = new HotelAvailableRequestDTO(destination, begin, end);

		return new ResponseEntity<List<HotelAvailableResponseDTO>> (service.listHotelResponse(dto),HttpStatus.OK);
	}
	
	@PostMapping(value="/hotels/bookings",consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookingDTO> createBookingHotel(@RequestBody BookingRequestHotelDTO dto, 
			                                HttpServletRequest request){
	    Integer idUsuario = (Integer) request.getAttribute("id_usuario"); // Extraer id_usuario del token
	    if (idUsuario == null) {
	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }

	    dto.setIdUser(idUsuario); // Asignar id_usuario extraído del token al DTO
		BookingDTO dtoResponse= service.createBookingForHotel(dto);
		return dtoResponse!=null?new ResponseEntity<>(dtoResponse,HttpStatus.OK):
			            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping(value="/hotels/bookings/confirm",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> confirmReservat(@RequestParam("idOccupation")int idOccupation,
			                                    @RequestParam("idBooking")int idBooking, HttpServletRequest request) {
	    //   Extraer id_usuario del token
	    Integer idUsuario = (Integer) request.getAttribute("id_usuario");
	    if (idUsuario == null) {
	        return new ResponseEntity<>("Unauthorized: Missing or invalid token.", HttpStatus.UNAUTHORIZED);
	    }
	    try {
	        System.out.println("[BookingController] - Usuario autenticado: " + idUsuario);
           // Intentar confirmar la reserva
	        BookingDTO dtoResponse = service.confirmReservation(idOccupation, idBooking);

	        // Si la confirmación fue exitosa, devolver el DTO con estado OK
	        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
	    } catch (BlockExpiredException e) {
	        // Si el bloqueo ha expirado, devolver un mensaje adecuado al cliente
	        return new ResponseEntity<>("Booking Controller : Session expired or reservation block expired. Please start over.", HttpStatus.UNAUTHORIZED);
	    }
	
}
}