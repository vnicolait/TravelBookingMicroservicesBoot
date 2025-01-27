package booking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import entities.BookingEntity.MethodPay;
import entities.BookingEntity.StatusBooking;
import entities.BookingEntity.TypeBooking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT) // Excluir valores predeterminados (como 0)
public class BookingDTO {
	
	private int idBooking;
    private int idUser;
    private TypeBooking typeBooking;
    private LocalDateTime bookingDate;
    private StatusBooking status;
    private String methodPay;
    private BigDecimal amountTotal;
    private List<DetailsDTO> details; 
    //field added for response to microservice hotel
    private int idOccupation;
}
