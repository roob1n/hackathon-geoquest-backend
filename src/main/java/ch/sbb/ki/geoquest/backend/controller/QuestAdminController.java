package ch.sbb.ki.geoquest.backend.controller;

import ch.sbb.ki.geoquest.backend.controller.dto.QuestDTO;
import ch.sbb.ki.geoquest.backend.controller.dto.ResponseDetailedDTO;
import ch.sbb.ki.geoquest.backend.mapper.QuestMapper;
import ch.sbb.ki.geoquest.backend.mapper.ResponseMapper;
import ch.sbb.ki.geoquest.backend.persistence.QuestRepository;
import ch.sbb.ki.geoquest.backend.persistence.ResponseRepository;
import ch.sbb.ki.geoquest.backend.persistence.entity.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/quests")
@RequiredArgsConstructor
public class QuestAdminController {

    private final QuestRepository questRepository;
    private final ResponseRepository responseRepository;
    private final QuestMapper questMapper;
    private final ResponseMapper responseMapper;

    @GetMapping
    public List<QuestDTO> getAllQuests() {
        return questMapper.map(questRepository.findAll());
    }

    @GetMapping("/{id}")
    public List<ResponseDetailedDTO> getQuestById(@PathVariable("id") Long id) {

        List<Response> responses = responseRepository.findAllByQuestId(id);
        return responseMapper.map(responses);
    }

    @PostMapping()
    public ResponseEntity<String> createQuest(@RequestBody QuestDTO questDto) {
        try {
            questRepository.save(questMapper.map(questDto));

            return new ResponseEntity<>("Quest created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating quest: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
