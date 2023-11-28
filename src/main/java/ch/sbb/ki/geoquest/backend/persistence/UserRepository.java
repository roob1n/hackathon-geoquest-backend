package ch.sbb.ki.geoquest.backend.persistence;

import ch.sbb.ki.geoquest.backend.persistence.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

}
