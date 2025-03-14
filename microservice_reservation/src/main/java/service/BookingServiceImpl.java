package service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import booking.dto.BookingDTO;
import booking.dto.BookingMapper;
import booking.dto.BookingRequestFlightDTO;
import booking.dto.BookingRequestHotelDTO;
import booking.dto.DetailsDTO;
import entities.BookingEntity;
import entities.BookingEntity.MethodPay;
import entities.BookingEntity.StatusBooking;
import entities.BookingEntity.TypeBooking;
import entities.DetailsEntity;
import exceptions.BlockExpiredException;
import exceptions.CodeNotExist;
import exceptions.FieldsCannotBeNullException;
import exceptions.FlightNotFoundException;
import exceptions.InvalidDateRangeException;
import exceptions.UnAvailableCapacity;
import external.ExternalFlightClient;
import external.ExternalHotelClient;
import external.dto.FlightRequestDTO;
import external.dto.FlightResponseDTO;
import external.dto.HotelAvailableRequestDTO;
import external.dto.HotelAvailableResponseDTO;
import external.dto.RoomRentRequestDTO;
import external.dto.RoomRentResponseDTO;
import repository.BookingJpaRepository;

@Service
public class BookingServiceImpl implements BookingService {

	
	@Autowired
	ExternalFlightClient external;
	@Autowired
	BookingJpaRepository repository;
	@Autowired
	BookingMapper mapper;
	@Autowired
	ExternalHotelClient externalHotel;
	
	@Override
	public List<FlightResponseDTO> listFlightResponse(FlightRequestDTO dto) {
	    if(dto.getOrigin()==null || dto.getDestination()==null) {
	    	throw new FieldsCannotBeNullException("origin or destination cannot be null");
	    }
	    if(dto.getStartDate().isAfter(dto.getEndDate())) {
	    	throw new  InvalidDateRangeException("Start date cannot be after end date");
	    }
		return external.listFlightAvailable(dto);
	}

	@Override
	@Transactional
	public BookingDTO createBookingForFlight(BookingRequestFlightDTO bookingRequestDTO){
		// User has 10 minutes to reserve
	    FlightResponseDTO responseFlightExternal= external.flightAvailableExternalBlock(bookingRequestDTO.getCodeReserve(),
	    		                        bookingRequestDTO.getNumberPlaces()  );
	     if(responseFlightExternal==null) {
	    	 throw new FlightNotFoundException("Not found seat by "+bookingRequestDTO.getCodeReserve());
	     }
	     if(responseFlightExternal.getAvailableSeats()<bookingRequestDTO.getNumberPlaces()) {
	    	 throw new UnAvailableCapacity("Not capacity seats "+bookingRequestDTO.getCodeReserve());
	     }
	     	    
	  // Crear el DTO de reserva
	     BookingDTO dto = new BookingDTO();
	     dto.setIdUser(bookingRequestDTO.getIdUser());
	     dto.setTypeBooking(TypeBooking.VUELO);
	     dto.setBookingDate(LocalDateTime.now());
	     dto.setStatus(StatusBooking.CONFIRMADA);
	     MethodPay methodPay = bookingRequestDTO.getMethodPay();
	     dto.setMethodPay(methodPay.name());
	     dto.setAmountTotal(BigDecimal.valueOf(bookingRequestDTO.getNumberPlaces()).multiply(responseFlightExternal.getPrice()));

	     // Crear detalles de la reserva
	     DetailsDTO detailDTO = new DetailsDTO();
	     detailDTO.setIdFlight(bookingRequestDTO.getCodeReserve());
	     detailDTO.setQuantity(bookingRequestDTO.getNumberPlaces());
	     detailDTO.setAmount(BigDecimal.valueOf(bookingRequestDTO.getNumberPlaces()).multiply(responseFlightExternal.getPrice()));

	  // Añadir detalles al DTO de la reserva
	     dto.setDetails(new ArrayList<>());
	     dto.getDetails().add(detailDTO);

	     // Convertir el DTO a la entidad y asignar los detalles a la entidad de la reserva
	     BookingEntity bookingEntity = mapper.toEntity(dto);
	     
	     // Asignar el idReserva a los detalles en la entidad antes de guardar la entidad principal
	     for (DetailsEntity detailEntity : bookingEntity.getDetails()) {
	         detailEntity.setBooking(bookingEntity); // Relación inversa: asignar BookingEntity a DetailsEntity
	     }

	     // Guardar la reserva con sus detalles (CascadeType.ALL manejará la persistencia de los detalles)
	     repository.save(bookingEntity);
	     // Actualizar el número de asientos disponibles en el sistema externo
	     external.flightUpdateSeatExternal(bookingRequestDTO.getCodeReserve(), bookingRequestDTO.getNumberPlaces());

	     return dto;
}

	// Hotel
	
	@Override
	public List<HotelAvailableResponseDTO> listHotelResponse(HotelAvailableRequestDTO dto) {
		System.out.println("ENTRAAAA");
		//validate(dto);
		if(dto.getDestination()==null) {
	    	throw new FieldsCannotBeNullException("destination cannot be null");
		}	
		return externalHotel.listAvailableHotels(dto);
	}	
	
	
		
	
	
	//Validaciones de hotel y fechas
	//Compruebo que el hotel existe y tiene disponibilidad
	// que devolver necesito idOcupacion y idReserva
	public BookingDTO createBookingForHotel(BookingRequestHotelDTO bookingRequestDTO) {
	                                  
		RoomRentRequestDTO roomRentRentRequestDTO=new RoomRentRequestDTO(bookingRequestDTO.getIdHotel(),
				                                     bookingRequestDTO.getIdRoom(),
				                                     bookingRequestDTO.getNumberPlaces(),
				                                     bookingRequestDTO.getStartRent(),
				                                     bookingRequestDTO.getEndRent());
		
		ResponseEntity<RoomRentResponseDTO> responseHotelExternal= externalHotel.hotelAvailableExternalBlock(roomRentRentRequestDTO);
	    RoomRentResponseDTO responseHotel  = responseHotelExternal.getBody();

		BookingDTO dto=new BookingDTO();
		 dto.setIdUser(bookingRequestDTO.getIdUser());
	     dto.setTypeBooking(TypeBooking.HOTEL);
	     dto.setBookingDate(LocalDateTime.now());
	     dto.setStatus(StatusBooking.PENDIENTE);
	     MethodPay methodPay = bookingRequestDTO.getMethodPay();
	     dto.setMethodPay(methodPay.name());
	     dto.setAmountTotal(BigDecimal.valueOf(bookingRequestDTO.getNumberPlaces()).multiply(responseHotel.getPriceByNight()));
        
	     dto.setIdOccupation(responseHotel.getIdOccupation());
	     
	     // Crear detalles de la reserva
	     DetailsDTO detailDTO = new DetailsDTO();
	     detailDTO.setIdHotel(bookingRequestDTO.getIdHotel());
	     detailDTO.setIdRoom(bookingRequestDTO.getIdRoom());
	     detailDTO.setQuantity(bookingRequestDTO.getNumberPlaces());
	     detailDTO.setAmount(BigDecimal.valueOf(bookingRequestDTO.getNumberPlaces()).multiply(responseHotel.getPriceByNight()));
	  // Añadir detalles al DTO de la reserva
	     dto.setDetails(new ArrayList<>());
	     dto.getDetails().add(detailDTO);
	     // Convertir el DTO a la entidad y asignar los detalles a la entidad de la reserva
	     BookingEntity bookingEntity = mapper.toEntity(dto);
	     // Asignar el idReserva a los detalles en la entidad antes de guardar la entidad principal
	     for (DetailsEntity detailEntity : bookingEntity.getDetails()) {
	         detailEntity.setBooking(bookingEntity); // Relación inversa: asignar BookingEntity a DetailsEntity
	     }

	     // Guardar la reserva con sus detalles (CascadeType.ALL manejará la persistencia de los detalles)
	     repository.save(bookingEntity);
	     
	     
	     // Resolver la respuesta necesito idOcupacion para confirmar
	        return dto;
	}


	
	@Transactional
	public BookingDTO confirmReservation(int idOccupation, int idBooking) {

		BookingEntity entity= repository.findById(idBooking)
				.orElseThrow(()->new CodeNotExist("Code with "+idBooking+" not exist"));
		  
     	    // Verificar si la reserva está en estado PENDIENTE
		    if(entity.getStatus()==StatusBooking.PENDIENTE) {
		    	 try {
		             // Llamar al microservicio externo para confirmar la ocupación
		             boolean isConfirmed = externalHotel.confirmReservationExternal(idOccupation);

		             if (isConfirmed) {
		                 // Si la confirmación es exitosa, actualizar el estado de la reserva
		                 entity.setStatus(StatusBooking.CONFIRMADA);
		                 repository.save(entity);
		                 return mapper.toDto(entity);
		             } else {
		                 // Si la confirmación falla, lanzar una excepción
			             throw new BlockExpiredException("Microservices Booking :The reservation block has expired. Please start over.");
		             }

		         } catch (BlockExpiredException e) {
		             // Lanza una excepción para indicar que el bloqueo de la habitación ha expirado
		             throw new BlockExpiredException("The reservation block has expired. Please start over.");
	}
	
		    }
		    // Si la reserva no está en estado PENDIENTE, no se puede confirmar
		    throw new IllegalStateException("The booking cannot be confirmed due to invalid status.");
	}
	// Implements Method Confirm Booking but before ask 
	private void validate(HotelAvailableRequestDTO dto) {
		if(dto.getFrom()==null || dto.getTo()==null) {
			//Excepciones personalizadas
	        throw new InvalidDateRangeException("Start and End dates cannot be null.");

		}		
		if(dto.getFrom().isAfter(dto.getTo())) {
	        throw new InvalidDateRangeException("Start date cannot be after End date");

		}
}

}