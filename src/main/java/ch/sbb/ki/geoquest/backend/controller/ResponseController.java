package ch.sbb.ki.geoquest.backend.controller;

import ch.sbb.ki.geoquest.backend.controller.dto.ResponseDTO;
import ch.sbb.ki.geoquest.backend.mapper.PointMapper;
import ch.sbb.ki.geoquest.backend.persistence.QuestRepository;
import ch.sbb.ki.geoquest.backend.persistence.ResponseRepository;
import ch.sbb.ki.geoquest.backend.persistence.UserRepository;
import ch.sbb.ki.geoquest.backend.persistence.entity.Quest;
import ch.sbb.ki.geoquest.backend.persistence.entity.Response;
import ch.sbb.ki.geoquest.backend.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/response")
@RequiredArgsConstructor
public class ResponseController {

    private final QuestRepository questRepository;
    private final ResponseRepository responseRepository;
    private final UserRepository userRepository;
    private final PointMapper pointMapper;

    @PostMapping
    public void postTransferResponse(@RequestBody ResponseDTO responseDTO) {
        final User user = userRepository.findById(responseDTO.getUserId()).orElseThrow();
        final Quest quest = questRepository.findById(responseDTO.getQuestId()).orElseThrow();
        final Response response = Response.builder()
                .user(user)
                .quest(quest)
                .transferTime(responseDTO.getTimeInSeconds())
                .platformLocation(pointMapper.map(responseDTO.getPoint()))
                .build();
        responseRepository.save(response);
    }
}
