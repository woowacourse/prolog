package wooteco.prolog.studylog.application;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudylogSessionServiceTest {


    @Mock
    private StudylogRepository studylogRepository;
    @InjectMocks
    private StudylogSessionService sessionService;

    @DisplayName("syncStudylogSession()를 호출하면, 모든 학습로그의 세션이 미션의 세션 정보로 동기화된다.")
    @Test
    void syncStudylogSession() {
        //given
        Member member = new Member("amaran-th", "아마란스", Role.CREW, 1L, "https://");
        Session sessionOfMission = new Session("백엔드 레벨 1 자바");
        Mission mission = new Mission("백엔드 체스", sessionOfMission);
        Session session = new Session("백엔드 레벨 100 코틀린");
        Studylog studylog = new Studylog(member, "제목", "내용", session, mission, Lists.emptyList());
        List<Studylog> studylogs = new ArrayList<>();
        studylogs.add(studylog);

        //when
        when(studylogRepository.findAll()).thenReturn(studylogs);
        sessionService.syncStudylogSession();
        boolean actual = studylogRepository.findAll().stream()
            .map(Studylog::getSession)
            .filter((afterSession) -> afterSession.equals(sessionOfMission))
            .count() == studylogs.size();

        //then
        assertThat(actual).isTrue();
    }
}
