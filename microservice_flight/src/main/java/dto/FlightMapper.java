package dto;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.model.FlightEntity;

@Mapper(componentModel = "spring")
public interface FlightMapper {
   
    @Mapping(source = "dateTimeDeparture", target = "startDate")
    @Mapping(source = "dateTimeArrival", target = "endDate")
	FlightDTO toDTO(FlightEntity flightEntity);
  
    List<FlightDTO> toListDTO(List<FlightEntity> flightEntity);
	
}
