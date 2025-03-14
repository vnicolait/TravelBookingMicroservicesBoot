package com.domain;





import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="ocupaciones")
public class OccupationEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_ocupacion")
    private int idOccupation;
    @Column(name="fecha_inicio")
    private LocalDate from;
    @Column(name="fecha_fin")
    private LocalDate to;
    @Column(name="cantidad")
	private int occupiedRooms;
    @Column(name="estado")
    @Enumerated(EnumType.STRING) // Se añade esta anotación
    private StateRoom stateRoom;
    
    private LocalDateTime block_expiration;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="id_habitacion",referencedColumnName = "id_habitacion")
	private RoomEntity room;
	
	public enum StateRoom{
		BLOQUEADO,
		RESERVADO,
		FINALIZADO,
		CANCELADO
	}
	public OccupationEntity(){}
	public int getIdOccupation() {
		return idOccupation;
	}
	public void setIdOccupation(int idOccupation) {
		this.idOccupation = idOccupation;
	}
	public LocalDate getFrom() {
		return from;
	}
	public void setFrom(LocalDate from) {
		this.from = from;
	}
	public LocalDate getTo() {
		return to;
	}
	public void setTo(LocalDate to) {
		this.to = to;
	}
	public int getOccupiedRooms() {
		return occupiedRooms;
	}
	public void setOccupiedRooms(int occupiedRooms) {
		this.occupiedRooms = occupiedRooms;
	}
	public StateRoom getStateRoom() {
		return stateRoom;
	}
	public void setStateRoom(StateRoom stateRoom) {
		this.stateRoom = stateRoom;
	}
	public RoomEntity getRoom() {
		return room;
	}
	public void setRoom(RoomEntity room) {
		this.room = room;
	}
	public LocalDateTime getBlock_expiration() {
		return block_expiration;
	}
	public void setBlock_expiration(LocalDateTime block_expiration) {
		this.block_expiration = block_expiration;
	}

    
	
}
