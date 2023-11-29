package ch.sbb.ki.geoquest.backend.controller.dto;

import ch.sbb.ki.geoquest.backend.persistence.entity.TransactionType;
import lombok.Value;

@Value
public class ResponseDTO {
    TransactionType type;
    Long userId;
    Long questId;
    int timeInSeconds;
    PointDTO point;
}
