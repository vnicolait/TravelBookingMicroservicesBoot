package dto;

import org.mapstruct.Mapper;

import com.domain.HotelEntity;

@Mapper(componentModel = "spring")
public interface HotelMapper {

	HotelDTO toDto(HotelEntity entity);
	HotelEntity toEntity(HotelDTO hotelDto);
}
