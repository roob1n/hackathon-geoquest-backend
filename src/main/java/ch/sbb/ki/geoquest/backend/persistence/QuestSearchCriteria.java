package ch.sbb.ki.geoquest.backend.persistence;

import java.util.Date;

import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QuestSearchCriteria {
    private Point<G2D> location;
    private Double radius;
    private Date date;
    @Builder.Default
    private boolean onlyNotEnoughResponses = false;
}
