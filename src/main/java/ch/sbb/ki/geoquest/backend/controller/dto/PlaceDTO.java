package ch.sbb.ki.geoquest.backend.controller.dto;

import lombok.Value;

@Value
public class PlaceDTO {
    String location;
    String sloid;
    String displayName;
}
