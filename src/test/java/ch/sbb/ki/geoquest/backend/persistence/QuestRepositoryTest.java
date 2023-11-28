package ch.sbb.ki.geoquest.backend.persistence;

import ch.sbb.ki.geoquest.backend.persistence.entity.Quest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ActiveProfiles("real-db")
class QuestRepositoryTest {

    @Autowired
    private QuestRepository questRepository;

    @Test
    public void whenFindAll_thenReturnNonEmptyList() {
        List<Quest> quests = questRepository.findAll();
        assertFalse(quests.isEmpty(), "Quest list should not be empty");
    }

}
