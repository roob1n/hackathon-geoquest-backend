package ch.sbb.ki.geoquest.backend.persistence;

import ch.sbb.ki.geoquest.backend.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
