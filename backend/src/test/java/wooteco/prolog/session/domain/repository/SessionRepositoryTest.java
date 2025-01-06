package wooteco.prolog.session.domain.repository;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.session.domain.Session;
import wooteco.support.utils.RepositoryTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
class SessionRepositoryTest {

    @Autowired
    private SessionRepository sessionRepository;

    @Test
    void findAllOrderByIdDesc() {
        sessionRepository.save(new Session("name1"));
        sessionRepository.save(new Session("name2"));

        List<Session> sessions = sessionRepository.findAllByOrderByIdDesc();

        assertThat(sessions.get(0).getName()).isEqualTo("name2");
        assertThat(sessions.get(1).getName()).isEqualTo("name1");
    }

    @Test
    void findAllByIdInOrderByIdDesc() {
        Session session1 = sessionRepository.save(new Session("name1"));
        Session session2 = sessionRepository.save(new Session("name2"));
        Session session3 = sessionRepository.save(new Session("name3"));
        Session session4 = sessionRepository.save(new Session("name4"));

        List<Session> sessions = sessionRepository.findAllByIdInOrderByIdDesc(
            Lists.newArrayList(session4.getId(), session2.getId()));

        assertThat(sessions.get(0).getName()).isEqualTo("name4");
        assertThat(sessions.get(1).getName()).isEqualTo("name2");
    }
}
