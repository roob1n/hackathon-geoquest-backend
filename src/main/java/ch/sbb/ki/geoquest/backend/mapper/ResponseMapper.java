package ch.sbb.ki.geoquest.backend.mapper;

import ch.sbb.ki.geoquest.backend.controller.dto.ResponseDTO;
import ch.sbb.ki.geoquest.backend.persistence.entity.Response;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PlaceMapper.class, TrackMapper.class})
public interface ResponseMapper {
    ResponseDTO map(Response response);
}
