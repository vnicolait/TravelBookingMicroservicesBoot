package com.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.HotelEntity;
import com.domain.OccupationEntity;
import com.domain.RoomEntity;
import com.exception.CodeNotExist;
import com.exception.InvalidDateRangeException;
import com.repository.HotelJpaRepository;
import com.repository.OccupationJpaRepository;

import dto.HotelDTO;

@Service
public class HotelServiceImpl implements HotelService{

	@Autowired
	HotelJpaRepository repository;
//	@Autowired
//	OccupationJpaRepository orepositiry;
//	@Override
	public List<HotelDTO> findAvailableHotelsWithRoomsBetweenDatesSQL(String location, LocalDate fechaInicio,
			LocalDate fechaFin) {
		   System.out.println(">>>>>>>>>> service " + location);
		   System.out.println(">>>>>>>>>> service " + fechaInicio);
		   System.out.println(">>>>>>>>>> service " + fechaFin);

		List<Object[]> results= repository.findAvailableHotelsWithRoomsSQL(location, fechaInicio, fechaFin);
	   System.out.println(">>>>>>>>>> " + results);
		List<HotelDTO> dtos=new ArrayList<>();
		for(Object[] row:results) {
			
			int idHotel=(Integer)row[0];
			System.out.println("DTO +idHotel+ " + idHotel);

			String nombre=(String)row[1];
			String ubicacion=(String)row[2];
			int idRoom=(Integer)row[3];
			BigDecimal precio=(BigDecimal)row[4];
			int numbersRooms=(Integer)row[5];
		/*
			java.sql.Date sqlFechaInicioOcupacion = (java.sql.Date) row[5];
	        LocalDate fechaInicioOcupacion = (sqlFechaInicioOcupacion != null) ? sqlFechaInicioOcupacion.toLocalDate() : null;
	        
	        java.sql.Date sqlFechaFinOcupacion = (java.sql.Date) row[6];
	        LocalDate fechaFinOcupacion = (sqlFechaFinOcupacion != null) ? sqlFechaFinOcupacion.toLocalDate() : null;
		*/ 
			LocalDate fechaInicioOcupacion = Optional.ofNullable((java.sql.Date) row[6])
                .map(java.sql.Date::toLocalDate)
                .orElse(null);

            LocalDate fechaFinOcupacion = Optional.ofNullable((java.sql.Date) row[7])
              .map(java.sql.Date::toLocalDate)
              .orElse(null);
	        HotelDTO hotel=new HotelDTO(idHotel, nombre, ubicacion, idRoom, precio ,numbersRooms, fechaInicioOcupacion,fechaFinOcupacion);
		     dtos.add(hotel);
		}
		return dtos;
	}
//	@Override
//	public List<HotelEntity> findAvailableHotelsWithRoomsBetweenDatesJPA(String location, LocalDate fechaInicio,
//			LocalDate fechaFin) {
//		return repository.findAvailableHotelsWithRoomsJPA(location, fechaInicio, fechaFin);
//	}
//	@Override
//	public HotelDTO findByHotelById(int idHotel) {
//		HotelEntity entity=repository.findById(idHotel).orElse(null);
//		HotelDTO dto=new HotelDTO(entity.getIdHotel(),
//				                  entity.getName(),
//				                  entity.getLocation(),
//				                  entity.getRooms().stream()
//				                      .filter(idRoom->idRoom.getIdRoom()).distinct(),
//				                   entity.getRooms()   )
//		return null;
//	}
//	
	@Override
	public HotelDTO findByHotelById(int idHotel) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
