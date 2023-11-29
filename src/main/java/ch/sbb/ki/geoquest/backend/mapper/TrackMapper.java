package ch.sbb.ki.geoquest.backend.mapper;

import ch.sbb.ki.geoquest.backend.controller.dto.TrackDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
abstract class TrackMapper {
    public TrackDTO map(String sloid) {
        return sloid == null ? null : new TrackDTO(sloid, "Haltekante %s".formatted(getHaltekante(sloid)));
    }

    public String map(TrackDTO trackDTO) {
        if(trackDTO != null) {
            return trackDTO.getSloid();
        } else {
            return null;
        }
    }

    private String getHaltekante(String sloid) {
        final String[] parts = sloid.split(":");
        return parts.length >= 6 ? parts[5] : "unknown";
    }
}
