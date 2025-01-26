package external.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor // Constructor sin parámetros
@AllArgsConstructor // Constructor con todos los parámetros
@Getter
@Setter
public class RoomRentRequestDTO {

	private int idHotel;
    private int idRoom;
    private int cantidad;
    private LocalDateTime startRent;
    private LocalDateTime endRent;
    
   
}
