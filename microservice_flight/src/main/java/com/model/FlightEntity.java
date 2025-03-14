package com.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="vuelos")
public class FlightEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_vuelo")
    private int idFlight;
    private String company;
	@Column(name="origen")
    private String origin;
	@Column(name="destino")
    private String destination;
	@Column(name="fecha_hora_salida")
    private LocalDateTime dateTimeDeparture;
	@Column(name="fecha_hora_llegada")
    private LocalDateTime dateTimeArrival;
	@Column(name="precio")
    private BigDecimal price;
	@Column(name="asientos_disponibles")
    private int availableSeats;
	@OneToMany(mappedBy = "flight")
    @JsonManagedReference  // Se coloca en el lado "uno" de la relación
	private List<ItinerariesEntity>itineraries;
	
	@Column(name = "blocked_seats")
    private int blockedSeats;

    @Column(name = "block_expiration")
    private LocalDateTime blockExpiration;
	
    public FlightEntity() {}

	public int getIdFlight() {
		return idFlight;
	}

	public void setIdFlight(int idFlight) {
		this.idFlight = idFlight;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public LocalDateTime getDateTimeDeparture() {
		return dateTimeDeparture;
	}

	public void setDateTimeDeparture(LocalDateTime dateTimeDeparture) {
		this.dateTimeDeparture = dateTimeDeparture;
	}

	public LocalDateTime getDateTimeArrival() {
		return dateTimeArrival;
	}

	public void setDateTimeArrival(LocalDateTime dateTimeArrival) {
		this.dateTimeArrival = dateTimeArrival;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public List<ItinerariesEntity> getItineraries() {
		return itineraries;
	}

	public void setItineraries(List<ItinerariesEntity> itineraries) {
		this.itineraries = itineraries;
	}

	public int getBlockedSeats() {
		return blockedSeats;
	}

	public void setBlockedSeats(int blockedSeats) {
		this.blockedSeats = blockedSeats;
	}

	public LocalDateTime getBlockExpiration() {
		return blockExpiration;
	}

	public void setBlockExpiration(LocalDateTime blockExpiration) {
		this.blockExpiration = blockExpiration;
	}

	@Override
	public String toString() {
		return "FlightEntity [idFlight=" + idFlight + ", company=" + company + ", origin=" + origin + ", destination="
				+ destination + ", dateTimeDeparture=" + dateTimeDeparture + ", dateTimeArrival=" + dateTimeArrival
				+ ", price=" + price + ", availableSeats=" + availableSeats + ", itineraries=" + itineraries
				+ ", blockedSeats=" + blockedSeats + ", blockExpiration=" + blockExpiration + "]";
	}
    
    
}
