package ch.sbb.ki.geoquest.backend.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Quest {

	@Id
	Long id;
}
