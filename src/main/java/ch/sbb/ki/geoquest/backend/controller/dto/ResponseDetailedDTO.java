package ch.sbb.ki.geoquest.backend.controller.dto;

import ch.sbb.ki.geoquest.backend.persistence.entity.TransactionType;
import lombok.Value;

@Value
public class ResponseDetailedDTO {
    UserDTO user;
    int timeInSeconds;
    PointDTO point;
}
