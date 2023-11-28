package ch.sbb.ki.geoquest.backend.persistence;

import ch.sbb.ki.geoquest.backend.persistence.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

}
