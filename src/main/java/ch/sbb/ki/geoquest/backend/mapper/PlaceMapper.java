package ch.sbb.ki.geoquest.backend.mapper;

import ch.sbb.ki.geoquest.backend.controller.dto.PlaceDTO;
import ch.sbb.ki.geoquest.backend.persistence.entity.Place;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
interface PlaceMapper {
    List<PlaceDTO> map(List<Place> places);
    PlaceDTO map(Place place);
    default String map(Point<G2D> point) {
        return point.toString();
    }
}
