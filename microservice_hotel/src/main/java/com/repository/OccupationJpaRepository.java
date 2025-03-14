                       package com.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.domain.OccupationEntity;
import com.domain.RoomEntity;

public interface OccupationJpaRepository extends JpaRepository<OccupationEntity, Integer> {
    @Query("Select r from RoomEntity r WHERE r.idRoom=?1 AND NOT EXISTS ( "
    		+ "Select o from OccupationEntity o WHERE o.room.idRoom=r.idRoom AND "
    		+ "o.from<?3 AND o.to>?2)")
	RoomEntity searchRoomAvailableByDates(int idRoom, LocalDate from, LocalDate end);


/* @Query("SELECT r FROM RoomEntity r WHERE r.idRoom = ?1 AND NOT EXISTS (" +
            "SELECT o FROM OccupationEntity o WHERE o.room.idRoom = r.idRoom AND " +
                "o.fecha_inicio < ?3 AND o.fecha_fin > ?2)")
*/
    
    /* ADD FUNCTION CURRENT*/
   // @Modifying
    @Transactional
    @Query(value="INSERT INTO ocupaciones (id_habitacion, fecha_inicio, fecha_fin, cantidad, estado, ultima_modificacion, block_expiration) VALUES "
           + "(:idRoom, :from, :to, :quantity, 'BLOQUEADO', NOW(), NOW() + INTERVAL '5 minutes') RETURNING id_ocupacion", 
           nativeQuery = true)
    int rentRoomOfHotel(@Param("idRoom") int idRoom, 
                         @Param("from") LocalDateTime from,
                         @Param("to") LocalDateTime to,
                         @Param("quantity") int quantity);
    
    @Modifying
    @Transactional
    @Query("UPDATE OccupationEntity o SET o.stateRoom = 'RESERVADO' WHERE o.idOccupation = ?1")
    void confirmReservationRoom(int idOccupation);
    
    @Transactional
    @Modifying
    @Query(value="DELETE FROM ocupaciones WHERE estado = 'BLOQUEADO' AND block_expiration < NOW()",nativeQuery=true)
    int releaseExpiredBlocks();
}