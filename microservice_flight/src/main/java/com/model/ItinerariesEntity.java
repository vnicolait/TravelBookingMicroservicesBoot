package com.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="itinerarios")
public class ItinerariesEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_itinerario")
    private int idItineraries;
	@Column(name="origen")
    private String origin;
	@Column(name="destino")
    private String destination;
	@Column(name="fecha_hora_salida")
    private LocalDateTime dateTimeDeparture;
	@Column(name="fecha_hora_llegada")
    private LocalDateTime dateTimeArrival;
	@ManyToOne
	@JoinColumn(name="id_vuelo", referencedColumnName = "id_vuelo")
    @JsonBackReference  // Se coloca en el lado "muchos" de la relación
    private FlightEntity flight;
	
	public ItinerariesEntity() {}

	public int getIdItineraries() {
		return idItineraries;
	}

	public void setIdItineraries(int idItineraries) {
		this.idItineraries = idItineraries;
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

	public FlightEntity getFlight() {
		return flight;
	}

	public void setFlight(FlightEntity flight) {
		this.flight = flight;
	}
	
	

}
