package ch.sbb.ki.geoquest.backend.mapper;

import ch.sbb.ki.geoquest.backend.controller.dto.QuestDTO;
import ch.sbb.ki.geoquest.backend.persistence.entity.Quest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = PlaceMapper.class)
public interface QuestMapper {
    List<QuestDTO> map(List<Quest> quests);

    QuestDTO map(Quest quest);
}
