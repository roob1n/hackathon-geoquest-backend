package ch.sbb.ki.geoquest.backend.mapper;

import ch.sbb.ki.geoquest.backend.controller.dto.PointDTO;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface PointMapper {
    default PointDTO map(Point<G2D> point) {
        return new PointDTO(point.getPosition().getLat(), point.getPosition().getLon());
    }
}
