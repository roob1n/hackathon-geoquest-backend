package ch.sbb.ki.geoquest.backend.mapper;

import ch.sbb.ki.geoquest.backend.controller.dto.PlaceDTO;
import ch.sbb.ki.geoquest.backend.persistence.entity.Place;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PointMapper.class)
interface PlaceMapper {
    @Mapping(target = "point", source = "location")
    PlaceDTO map(Place place);
}
