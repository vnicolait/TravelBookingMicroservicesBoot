package dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.domain.HotelEntity;

public class HotelDTO {
	 private Integer idHotel;
	 private String name;
	 private String location;
	 private int idRoom;
	 private int numbersRooms;
	 private BigDecimal priceByNight;
	 private LocalDate checkIn;
	 private LocalDate checkOut;

	 public HotelDTO() {}

	 
	public HotelDTO(Integer idHotel, String name, String location, int idRoom, BigDecimal priceByNight ,int numbersRooms, LocalDate checkIn,
			LocalDate checkOut) {
		super();
		this.idHotel = idHotel;
		this.name = name;
		this.location = location;
		this.idRoom = idRoom;
		this.numbersRooms=numbersRooms;
		this.priceByNight=priceByNight;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
	}


	public Integer getIdHotel() {
		return idHotel;
	}


	public void setIdHotel(Integer idHotel) {
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


	public LocalDate getCheckIn() {
		return checkIn;
	}


	public void setCheckIn(LocalDate checkIn) {
		this.checkIn = checkIn;
	}


	public LocalDate getCheckOut() {
		return checkOut;
	}


	public void setCheckOut(LocalDate checkOut) {
		this.checkOut = checkOut;
	}


	public int getNumbersRooms() {
		return numbersRooms;
	}


	public void setNumbersRooms(int numbersRooms) {
		this.numbersRooms = numbersRooms;
	}

	
	 
}
