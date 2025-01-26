package booking.dto;

import java.time.LocalDateTime;

import entities.BookingEntity.MethodPay;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookingRequestHotelDTO {

	private int idUser;
	private int idHotel;
	private int idRoom;
	private int numberPlaces;
	private MethodPay methodPay; //???
	private LocalDateTime startRent; //???
	private LocalDateTime endRent; //???
	
}
