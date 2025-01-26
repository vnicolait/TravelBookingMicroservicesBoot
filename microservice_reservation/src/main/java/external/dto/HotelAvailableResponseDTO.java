package external.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor // Constructor sin parámetros
@AllArgsConstructor // Constructor con todos los parámetros
@Getter
@Setter
public class HotelAvailableResponseDTO {

	private int idHotel;
    private String name;
    private String location;
    private int idRoom;
    private BigDecimal priceByNight;
    private LocalDate checkIn;
    private LocalDate checkOut;
}
