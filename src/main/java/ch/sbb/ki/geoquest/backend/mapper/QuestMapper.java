package ch.sbb.ki.geoquest.backend.mapper;

import ch.sbb.ki.geoquest.backend.controller.dto.QuestDTO;
import ch.sbb.ki.geoquest.backend.persistence.entity.Quest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PlaceMapper.class, TrackMapper.class})
public interface QuestMapper {
    List<QuestDTO> map(List<Quest> quests);

    @Mapping(target = "instruction", source = "instructions")
    @Mapping(target = "location", source = "place")
    QuestDTO map(Quest quest);
}
