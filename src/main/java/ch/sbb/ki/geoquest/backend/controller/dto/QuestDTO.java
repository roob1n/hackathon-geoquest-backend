package ch.sbb.ki.geoquest.backend.controller.dto;

import ch.sbb.ki.geoquest.backend.persistence.entity.TransactionType;
import lombok.Value;

import java.util.Date;

@Value
public class QuestDTO {
    Long id;
    TransactionType type;
    String title;
    String instructions;
    PlaceDTO place;
    Integer minResponses;
    Integer baseReward;
    Date startDate;
    Date endDate;
    String transferFrom;
    String transferTo;
    String platform;
}
