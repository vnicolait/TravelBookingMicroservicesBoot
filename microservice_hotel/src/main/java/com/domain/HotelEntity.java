package com.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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

@Entity
@Table(name="hoteles")
public class HotelEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_hotel")
    private int idHotel;
	@Column(name="nombre")
    private String name;
	@Column(name="ubicacion")
    private String location;
	@Column(name="estrellas")
    private int stars;
    @Enumerated(EnumType.STRING) // Se añade esta anotación
    @Column(name="categoria")
    private Category category;
    @Column(name="total_habitaciones")
    private int numbersRooms;
    @JsonManagedReference
	@OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
	List<RoomEntity>rooms;
	
	public enum Category {
		ECONOMICO,
		PREMIUM,
		LUJO
	}
	
	public HotelEntity(){}

	public int getIdHotel() {
		return idHotel;
	}

	public void setIdHotel(int idHotel) {
		this.idHotel = idHotel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<RoomEntity> getRooms() {
		return rooms;
	}

	public void setRooms(List<RoomEntity> rooms) {
		this.rooms = rooms;
	}

	public int getNumbersRooms() {
		return numbersRooms;
	}

	public void setNumbersRooms(int numbersRooms) {
		this.numbersRooms = numbersRooms;
	}
	
	
}
