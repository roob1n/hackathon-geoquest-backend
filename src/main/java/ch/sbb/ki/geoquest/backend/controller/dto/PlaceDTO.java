package ch.sbb.ki.geoquest.backend.controller.dto;

import lombok.Value;

@Value
public class PlaceDTO {
    PointDTO point;
    String sloid;
    String displayName;
}
