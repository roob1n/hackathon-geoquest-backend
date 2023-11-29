package ch.sbb.ki.geoquest.backend.controller.dto;

import ch.sbb.ki.geoquest.backend.persistence.entity.TransactionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Value;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    TrackDTO startTrack;
    TrackDTO endTrack;
    TrackDTO platform;
    List<ResponseDTO> responses = new ArrayList<>();
}
