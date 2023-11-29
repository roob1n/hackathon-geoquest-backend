package ch.sbb.ki.geoquest.backend.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Value;

@Value
public class TrackDTO {
    String sloid;
    String displayName;

    @JsonCreator
    public static TrackDTO fromString(String value) {
        return new TrackDTO(value, null);
    }
}
