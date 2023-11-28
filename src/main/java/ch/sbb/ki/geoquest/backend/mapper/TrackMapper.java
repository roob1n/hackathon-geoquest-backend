package ch.sbb.ki.geoquest.backend.mapper;

import ch.sbb.ki.geoquest.backend.controller.dto.TrackDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface TrackMapper {
    default TrackDTO map(String sloid) {
        return new TrackDTO(sloid, "Haltekante %s".formatted(getHaltekante(sloid)));
    }

    default String getHaltekante(String sloid) {
        final String[] parts = sloid.split(":");
        return parts.length >= 6 ? parts[5] : "unknown";
    }
}
