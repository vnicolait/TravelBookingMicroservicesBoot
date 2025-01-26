package booking.dto;

import java.util.List;

import org.mapstruct.Mapper;

import entities.BookingEntity;
import entities.DetailsEntity;

@Mapper(componentModel = "spring")
public interface BookingMapper {
	BookingEntity toEntity(BookingDTO bookingDTO);
   
	BookingDTO toDto(BookingEntity entity);
	
	DetailsEntity toDetailEntoty(DetailsDTO detailsDTO);
	
	DetailsDTO toDetailsDto(DetailsEntity entity);

    List<DetailsDTO> toDetailsDTOList(List<DetailsEntity> entities);

    List<DetailsEntity> toDetailsEntityList(List<DetailsDTO> dtos);

}
