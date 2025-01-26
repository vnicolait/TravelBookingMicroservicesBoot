package service;

import java.util.List;

import booking.dto.BookingDTO;
import booking.dto.BookingRequestFlightDTO;
import external.dto.FlightRequestDTO;
import external.dto.FlightResponseDTO;
import external.dto.HotelAvailableRequestDTO;
import external.dto.HotelAvailableResponseDTO;

public interface BookingService {

	List<FlightResponseDTO> listFlightResponse(FlightRequestDTO dto);
	 
	BookingDTO createBookingForFlight(BookingRequestFlightDTO bookingDTO);
	
	//Hotel
	List<HotelAvailableResponseDTO> listHotelResponse(HotelAvailableRequestDTO dto);
}
