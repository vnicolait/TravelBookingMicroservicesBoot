package com.domain;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="habitaciones")
public class RoomEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_habitacion")
    private int idRoom;
  
	
	@Column(name="precio_por_noche")
    private BigDecimal priceByNight;
	@Column(name="descripcion")
    private String description;
	@Column(name="cantidad")
	private int numberOfRooms;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="id_hotel", referencedColumnName="id_hotel")
	private HotelEntity hotel;
	
    @JsonManagedReference
	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
	private List<OccupationEntity> occupations;
	

	public RoomEntity(){}

	public int getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}


	public BigDecimal getPriceByNight() {
		return priceByNight;
	}

	public void setPriceByNight(BigDecimal priceByNight) {
		this.priceByNight = priceByNight;
	}

	

	public HotelEntity getHotel() {
		return hotel;
	}

	public void setHotel(HotelEntity hotel) {
		this.hotel = hotel;
	}

	public List<OccupationEntity> getOccupations() {
		return occupations;
	}

	public void setOccupations(List<OccupationEntity> occupations) {
		this.occupations = occupations;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}
	
	
}
