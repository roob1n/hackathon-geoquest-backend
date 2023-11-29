package ch.sbb.ki.geoquest.backend.persistence;

import java.util.List;

import ch.sbb.ki.geoquest.backend.persistence.entity.Quest;

public interface QuestRepositoryCustom {

    List<Quest> findByCriteria(QuestSearchCriteria searchCriteria);

}