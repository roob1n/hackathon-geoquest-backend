package ch.sbb.ki.geoquest.backend.mapper;

import ch.sbb.ki.geoquest.backend.controller.dto.PointDTO;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.geolatte.geom.crs.CoordinateReferenceSystems;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PointMapper {
    default PointDTO map(Point<G2D> point) {
        return point == null ? null : new PointDTO(point.getPosition().getLat(), point.getPosition().getLon());
    }

    default Point<G2D> map(PointDTO pointDTO) {
        return pointDTO == null ? null : new Point<>(new G2D(pointDTO.getLat(), pointDTO.getLon()), CoordinateReferenceSystems.WGS84);
    }
}
