package entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name="reservas")
public class BookingEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_reserva")
	private int idBooking;
	@Column(name="id_usuario")
    private int idUser;
	@Column(name="tipo_reserva")
	@Enumerated(EnumType.STRING)
	private TypeBooking typeBooking;
	@Column(name="fecha_reserva")
	private LocalDateTime bookingDate;
	@Column(name="estado")
	@Enumerated(EnumType.STRING)
	private StatusBooking status;
	@Column(name="metodo_pago")
	@Enumerated(EnumType.STRING)
	private MethodPay methodPay;
	@Column(name="monto_total")
	private BigDecimal amountTotal;
	@OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
	private List<DetailsEntity> details; //CascadeType si deseas que los cambios en BookingEntity se propaguen automáticamente a los detalles.
	
	 // Enums dentro de la entidad
    public enum StatusBooking {
        PENDIENTE,
        CONFIRMADA,
        CANCELADA
    }

    public enum TypeBooking {
        HOTEL,
        VUELO,
        COMBINADA
    }

    public enum MethodPay {
        PAYPAL,
        TARJETA,
        TRANSFERENCIA
    }
}
