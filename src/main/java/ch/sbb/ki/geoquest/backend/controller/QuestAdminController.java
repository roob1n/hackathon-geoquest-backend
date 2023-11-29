package ch.sbb.ki.geoquest.backend.controller;

import ch.sbb.ki.geoquest.backend.controller.dto.QuestDTO;
import ch.sbb.ki.geoquest.backend.mapper.QuestMapper;
import ch.sbb.ki.geoquest.backend.persistence.QuestRepository;
import ch.sbb.ki.geoquest.backend.persistence.entity.Place;
import ch.sbb.ki.geoquest.backend.persistence.entity.Quest;
import ch.sbb.ki.geoquest.backend.persistence.entity.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin/quests")
@RequiredArgsConstructor
public class QuestAdminController {

    private final QuestRepository questRepository;
    private final QuestMapper questMapper;

    @GetMapping
    public List<QuestDTO> getAllQuests() {
        return questMapper.map(questRepository.findAll());
    }

    @PostMapping()
    public ResponseEntity<String> createQuest(@RequestBody QuestDTO questDto) {
        try {
            // Validate questDto if necessary
            questRepository.save(questMapper.map(questDto));

            // Save the quest to the database.saveQuest(questDto);
            return new ResponseEntity<>("Quest created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating quest: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
