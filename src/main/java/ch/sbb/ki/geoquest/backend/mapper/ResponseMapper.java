package ch.sbb.ki.geoquest.backend.mapper;

import ch.sbb.ki.geoquest.backend.controller.dto.QuestDTO;
import ch.sbb.ki.geoquest.backend.controller.dto.ResponseDTO;
import ch.sbb.ki.geoquest.backend.persistence.entity.Response;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PlaceMapper.class, TrackMapper.class, PointMapper.class})
public interface ResponseMapper {
    @Mapping(target = "timeInSeconds", source = "transferTime")
    @Mapping(target = "point", source = "platformLocation")
    ResponseDTO map(Response response);

    List<ResponseDTO> map(List<Response> responses);
}
