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
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Point<G2D> location; // You might need a custom type for GEOMETRY
    private String sloid;
    private String displayName;
}

