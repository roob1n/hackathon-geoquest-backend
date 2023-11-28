package ch.sbb.ki.geoquest.backend.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;

@Data
@Entity
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long questId;
    private Long userId;
    private Integer transferTime;
    private Point<G2D> platformLocation;
}
