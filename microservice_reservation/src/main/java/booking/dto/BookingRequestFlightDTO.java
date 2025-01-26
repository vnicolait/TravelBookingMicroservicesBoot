package booking.dto;

import entities.BookingEntity.MethodPay;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookingRequestFlightDTO {

	private int idUser;
	private int codeReserve;
	private int numberPlaces;
	private MethodPay methodPay;
}
