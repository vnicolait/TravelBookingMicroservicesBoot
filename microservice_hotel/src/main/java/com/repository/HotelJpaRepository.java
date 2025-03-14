package com.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.domain.HotelEntity;

public interface HotelJpaRepository extends JpaRepository<HotelEntity, Integer>{
	
//	@Query("SELECT h FROM HotelEntity h " +
//		       "JOIN h.rooms r " +
//		       "LEFT JOIN r.occupations o " +
//		       "WHERE h.location = :location " +
//		       "AND r.disponibility = true " +
//		       "AND (o.id_ocupacion IS NULL " +
//		       "     OR (o.fecha_fin < :fechaInicio " +
//		       "     OR o.fecha_inicio > :fechaFin))")
//		List<HotelEntity> findAvailableHotelsWithRoomsJPA(@Param("location") String location,
//		                                               @Param("fechaInicio") LocalDate fechaInicio,
//		                                               @Param("fechaFin") LocalDate fechaFin);

	
	@Query(value="SELECT h.id_hotel, h.nombre, h.ubicacion, r.id_habitacion, r.precio_por_noche, r.cantidad, o.fecha_inicio, o.fecha_fin from hoteles h JOIN habitaciones r ON "
			+ "h.id_hotel=r.id_hotel LEFT JOIN ocupaciones o ON r.id_habitacion=o.id_habitacion WHERE h.ubicacion =:location AND "
			+ "(o.id_ocupacion IS NULL OR (o.fecha_fin< :fechaInicio OR o.fecha_inicio> :fechaFin))", nativeQuery = true)
	List<Object[]> findAvailableHotelsWithRoomsSQL(@Param("location") String location,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin);

						

}
