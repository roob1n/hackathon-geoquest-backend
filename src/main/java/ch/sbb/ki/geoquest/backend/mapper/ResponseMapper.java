package ch.sbb.ki.geoquest.backend.mapper;

import ch.sbb.ki.geoquest.backend.controller.dto.ResponseDetailedDTO;
import ch.sbb.ki.geoquest.backend.persistence.entity.Response;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PlaceMapper.class, TrackMapper.class, PointMapper.class, UserMapper.class})
public interface ResponseMapper {
    @Mapping(target = "timeInSeconds", source = "transferTime")
    @Mapping(target = "point", source = "platformLocation")
    ResponseDetailedDTO map(Response response);

    List<ResponseDetailedDTO> map(List<Response> responses);
}
