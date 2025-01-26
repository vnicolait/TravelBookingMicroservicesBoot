package booking.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DetailsDTO {

	private int idReserve;
    private int idRoom;
    private int idFlight;
    private int quantity;
    private BigDecimal amount;
    private int idHotel; //field add after design
}
