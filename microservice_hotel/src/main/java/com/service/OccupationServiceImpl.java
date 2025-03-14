package com.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.domain.HotelEntity;
import com.domain.OccupationEntity;
import com.domain.OccupationEntity.StateRoom;
import com.domain.RoomEntity;
import com.exception.BlockExpiredException;
import com.exception.CodeNotExist;
import com.exception.InvalidDateRangeException;
import com.exception.RoomNotAvailableException;
import com.repository.HotelJpaRepository;
import com.repository.OccupationJpaRepository;
import com.repository.RoomJpaRepository;

import dto.RoomRentRequestDTO;
import dto.RoomRentResponseDTO;

@Service
public class OccupationServiceImpl implements OccupationService {

	@Autowired
	OccupationJpaRepository occupationRepository;
	@Autowired
	HotelJpaRepository hotelRepository;
	@Autowired
	RoomJpaRepository roomRepository;
	
	/*
	 * This method should update because database was change fields
	 * 
	 */
	@Override
	public void saveRentRoomOccupation(int idHotel, int idRoom, LocalDate startRent, LocalDate endRent) {
		validateDateRangeLocalDate(startRent, endRent);
		// Obtener el hotel por ID
		HotelEntity hotel = hotelRepository.findById(idHotel)
		        .orElseThrow(() -> new CodeNotExist("Hotel with ID " + idHotel + " does not exist"));

		// Buscar la habitación en la lista de habitaciones del hotel
		RoomEntity room = hotel.getRooms().stream()
		        .filter(r -> r.getIdRoom() == idRoom)
		        .findFirst()
		        .orElseThrow(() -> new CodeNotExist("Room with ID " + idRoom + " does not exist in Hotel with ID " + idHotel));       
		
		RoomEntity roomAvailable=occupationRepository.searchRoomAvailableByDates(room.getIdRoom(), startRent, endRent);
	     	 if (roomAvailable == null) {
		    	 throw new RoomNotAvailableException("Room is not available between " + startRent + " and " + endRent);
		     }
	    	 OccupationEntity oc=new OccupationEntity();
	    	 oc.setRoom(room);
	    	 oc.setFrom(startRent);
	    	 oc.setTo(endRent);
	    	 occupationRepository.save(oc);
	    // }
		  // Registrar en logs
		  //   logger.info("Room with ID {} successfully rented in Hotel {} from {} to {}.", idRoom, idHotel, startRent, endRent);
      }

	

	private void validateDateRangeLocalDate(LocalDate start, LocalDate end) {
		if(start==null || end==null) {
			//Excepciones personalizadas
	        throw new InvalidDateRangeException("Start and End dates cannot be null.");

		}
		if(start.isBefore(LocalDate.now())) {
	        throw new InvalidDateRangeException("Start date cannot be in the past.");

		}
		if(start.isAfter(end)) {
	        throw new InvalidDateRangeException("Start date cannot be after End date");

		}
	}

	private void validateDateRangeLocalDateTime(LocalDateTime start, LocalDateTime end) {
   
		if(start==null || end==null) {
			//Excepciones personalizadas
	        throw new InvalidDateRangeException("Start and End dates cannot be null.");

		}
		if(start.isBefore(LocalDateTime.now())) {
	        throw new InvalidDateRangeException("Start date cannot be in the past.");

		}
		if(start.isAfter(end)) {
	        throw new InvalidDateRangeException("Start date cannot be after End date");

		}
	}

	@Transactional
	public RoomRentResponseDTO rentRoomOfHotelCurrent(RoomRentRequestDTO dto) {
	HotelEntity hotelEntity=hotelRepository.findById(dto.getIdHotel()).orElseThrow(()-> new CodeNotExist("Hotel with ID " + dto.getIdHotel() + " does not exist"));
       validateDateRangeLocalDateTime(dto.getStartRent(),dto.getEndRent());
	   RoomEntity roomEntity= hotelEntity.getRooms().stream()
				                .filter(r->r.getIdRoom()==dto.getIdRoom())
				                   .findFirst().orElseThrow(()->new CodeNotExist("Room with ID " + dto.getIdRoom() + " does not exist"));
		if(roomEntity.getNumberOfRooms()<dto.getCantidad()) {
			throw new RoomNotAvailableException("Not Capacity Room is not available between ");
		}
		roomEntity.setNumberOfRooms(roomEntity.getNumberOfRooms()-dto.getCantidad());
	   int idOccupation=occupationRepository.rentRoomOfHotel(dto.getIdRoom(), dto.getStartRent(), dto.getEndRent(), dto.getCantidad());
	   
	    return new RoomRentResponseDTO(hotelEntity.getIdHotel(),roomEntity.getIdRoom(),roomEntity.getNumberOfRooms(),
	    		roomEntity.getPriceByNight(), dto.getStartRent(),dto.getEndRent(), idOccupation );
	}
	
	//TEST ABOUT RESPONSE 200 IT'S RIGHT BECAUSE NOT CHANGE STATE WHEN EXPIRED BUT
	// 200 NOT RIGHT
	@Transactional
	public boolean confirmReservationRoom(int idOccupation) {
       OccupationEntity entity=occupationRepository.findById(idOccupation).orElseThrow(()->new CodeNotExist("Hotel with ID "));
       if (entity.getStateRoom() == StateRoom.BLOQUEADO) {
    	   if (entity.getBlock_expiration().isBefore(LocalDateTime.now())) {
               System.out.println("TIME EXPIRED");
               
               throw new BlockExpiredException("The reservation block has expired, please start over.");
    	      // return false;
    	   } else {
               // Si el bloque no ha expirado, se puede confirmar
               occupationRepository.confirmReservationRoom(entity.getIdOccupation());

        	   occupationRepository.save(entity);
        	   return true;
           }
       } 
         return false;
       }
                   
         // return false;
	

	//@Scheduled(fixedRate = 60000) // Cada minuto
	public boolean releaseExpiredBlocks() {
	int unblock= occupationRepository.releaseExpiredBlocks();
		 return unblock>0?true:false;
		 
	
	}
}




/*
 * @Override
public void saveRentRoomOccupation(int idHotel, int idRoom, LocalDate startRent, LocalDate endRent) {
    // Validación: Verifica que la fecha de inicio no es posterior a la de fin
    if (startRent.isAfter(endRent)) {
        throw new InvalidDateRangeException("The start date cannot be after the end date");
    }

    // Buscar habitación y verificar que pertenece al hotel
    RoomEntity room = roomRepository.findRoomByIdAndHotelId(idRoom, idHotel)
            .orElseThrow(() -> new CodeNotExist("Room with ID " + idRoom + " does not exist in Hotel with ID " + idHotel));

    // Comprobar disponibilidad de la habitación para las fechas dadas
    RoomEntity roomAvailable = occupationRepository.searchRoomAvailableByDates(room.getIdRoom(), startRent, endRent);
    if (roomAvailable == null) {
        throw new RoomUnavailableException("Room with ID " + idRoom + " is not available between " + startRent + " and " + endRent);
    }

    // Crear y guardar la nueva ocupación
    OccupationEntity occupation = new OccupationEntity();
    occupation.setRoom(room); // Relaciona la ocupación con la habitación
    occupation.setFecha_inicio(startRent); // Establece la fecha de inicio
    occupation.setFecha_fin(endRent); // Establece la fecha de fin
    occupationRepository.save(occupation); // Guarda la ocupación
}
*/
