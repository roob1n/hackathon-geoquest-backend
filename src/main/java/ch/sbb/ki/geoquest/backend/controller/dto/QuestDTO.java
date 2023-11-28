package ch.sbb.ki.geoquest.backend.controller.dto;

import ch.sbb.ki.geoquest.backend.persistence.entity.TransactionType;
import lombok.Value;

import java.util.Date;

@Value
public class QuestDTO {
    Long id;
    TransactionType type;
    String title;
    String instruction;
    PlaceDTO location;
    Integer minResponses;
    Integer baseReward;
    Date startDate;
    Date endDate;
    TrackDTO transferFrom;
    TrackDTO transferTo;
    TrackDTO platform;
}
