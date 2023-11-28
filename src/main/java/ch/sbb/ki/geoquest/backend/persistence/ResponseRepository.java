package ch.sbb.ki.geoquest.backend.persistence;

import ch.sbb.ki.geoquest.backend.persistence.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {

}
