package ch.sbb.ki.geoquest.backend.controller;

import ch.sbb.ki.geoquest.backend.controller.dto.QuestDTO;
import ch.sbb.ki.geoquest.backend.mapper.QuestMapper;
import ch.sbb.ki.geoquest.backend.persistence.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/quests") // Base URL for quest-related operations
@RequiredArgsConstructor
public class QuestController {

    private final QuestRepository questRepository;
    private final QuestMapper questMapper;

    @GetMapping
    public List<QuestDTO> getAllQuests() {
        return questMapper.map(questRepository.findAll());
    }
}
