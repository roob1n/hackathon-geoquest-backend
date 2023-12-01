package ch.sbb.ki.geoquest.backend.controller;

import ch.sbb.ki.geoquest.backend.persistence.QuestRepository;
import ch.sbb.ki.geoquest.backend.persistence.ResponseRepository;
import ch.sbb.ki.geoquest.backend.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/count")
@RequiredArgsConstructor
public class CountAdminController {

    private final ResponseRepository responseRepository;
    private final UserRepository userRepository;
    private final QuestRepository questRepository;

    @GetMapping("/responses")
    public int getAllResponsesCount() {
        return responseRepository.findAll().size();
    }

    @GetMapping("/users")
    public int getAllUsersCount() {
        return userRepository.findAll().size();
    }

    @GetMapping("/quests")
    public int getAllQuestsCount() {
        return questRepository.findAll().size();
    }
}
