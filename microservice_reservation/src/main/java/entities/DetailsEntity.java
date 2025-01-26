package entities;

import java.math.BigDecimal;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor // Constructor sin parámetros
@AllArgsConstructor // Constructor con todos los parámetros
@Getter
@Setter
@Entity
@Table(name="detalle_reserva")
public class DetailsEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_detalle")
	private int idDetail;
	//@Column(name="id_reserva")
	//private int idBooking;
	@Column(name="id_habitacion")
	private int idRoom;
	@Column(name="id_vuelo")
	private int idFlight;
	@Column(name="cantidad")
	private int quantity;
	@Column(name="subtotal")
	private BigDecimal amount;
	@ManyToOne
	@JoinColumn(name="id_reserva",referencedColumnName = "id_reserva")
	private BookingEntity booking;
	
	//field add after design
	@Column(name="id_hotel")
	private int idHotel;
}
