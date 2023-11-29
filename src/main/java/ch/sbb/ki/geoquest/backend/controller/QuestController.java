package ch.sbb.ki.geoquest.backend.controller;

import ch.sbb.ki.geoquest.backend.controller.dto.QuestDTO;
import ch.sbb.ki.geoquest.backend.mapper.QuestMapper;
import ch.sbb.ki.geoquest.backend.persistence.QuestRepository;
import ch.sbb.ki.geoquest.backend.persistence.QuestSearchCriteria;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.geolatte.geom.builder.DSL;
import org.geolatte.geom.crs.CoordinateReferenceSystems;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/quests")
@RequiredArgsConstructor
public class QuestController {

    private final QuestRepository questRepository;
    private final QuestMapper questMapper;

    @CrossOrigin
    @GetMapping
    public List<QuestDTO> getQuests(
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon,
            @RequestParam(required = false) Double radius,
            @RequestParam(required = false) @Parameter(description = "is valid and as not enough responses") Boolean isOpened,
            @RequestParam(required = false) Long noResponseFromUserId
    ) {
        if ((lat == null || lon == null) && !(lat == null && lon == null)) {
            throwHttpError(HttpStatus.BAD_REQUEST, "Either set both lat and lon or none of them.");
        }
        Date date = Boolean.TRUE.equals(isOpened) ? new Date(): null;
        Point<G2D> location = (lat == null || lon == null) ? null : DSL.point(CoordinateReferenceSystems.WGS84, DSL.g(lon, lat));
        QuestSearchCriteria searchCriteria = QuestSearchCriteria.builder()
                .location(location)
                .radius(radius)
                .date(date)
                .onlyNotEnoughResponses(Boolean.TRUE.equals(isOpened))
                .noResponseFromUserId(noResponseFromUserId)
                .build();
        return questMapper.map(questRepository.findByCriteria(searchCriteria));
    }

    private void throwHttpError(HttpStatusCode status, String message) {
        ErrorResponseException ex = new ErrorResponseException(status);
        // sadly stil no more information to the client with: ErrorResponseException ex = new ErrorResponseException(status, ProblemDetail.forStatus(status), null, message, null);
        ex.setDetail(message);
        throw ex;
    }
}
