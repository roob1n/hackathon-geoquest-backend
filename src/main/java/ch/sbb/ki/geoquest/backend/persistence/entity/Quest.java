package ch.sbb.ki.geoquest.backend.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Quest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private TransactionType type;
	private String title;
	private String instructions;
	private Integer placeId;
	private Integer minResponses;
	private Integer baseReward;
	private Date startDate;
	private Date endDate;
	private String transferFrom;
	private String transferTo;
	private String platform;
}

