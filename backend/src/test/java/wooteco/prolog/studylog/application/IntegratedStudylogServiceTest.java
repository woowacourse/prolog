package wooteco.prolog.studylog.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.MissionRepository;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogRssFeedResponse;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogRead;
import wooteco.prolog.studylog.domain.repository.StudylogReadRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
class IntegratedStudylogServiceTest {

    @Autowired
    private StudylogService studylogService;
    @Autowired
    private StudylogReadRepository studylogReadRepository;
    @Autowired
    private StudylogRepository studylogRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private MissionRepository missionRepository;

    private Studylog studylog;
    private Member judy;
    private Session session;
    private Mission mission;

    @BeforeEach
    void setUp() {
        Member 주디 = new Member(1L, "xrabcde", "바다", Role.CREW, 1111L,
            "https://avatars.githubusercontent.com/u/56033755?v=4");
        judy = memberRepository.save(주디);
        session = sessionRepository.save(new Session("세션1"));
        mission = missionRepository.save(new Mission("미션", session));
        studylog = studylogRepository
            .save(new Studylog(judy, "제목", "내용", session, mission, new ArrayList<>()));
    }

    @DisplayName("findReadIds() : 멤버가 읽은 studylog들의 id들을 가져온다")
    @Test
    void findReadIds() {
        //given
        StudylogRead studylogRead = new StudylogRead(judy, studylog);
        studylogReadRepository.save(studylogRead);

        //when
        List<Long> actual = studylogService.findReadIds(judy.getId());

        //then
        assertThat(actual.get(0)).isEqualTo(studylog.getId());
    }

    @DisplayName("updateRead() : studylog를 읽은 상태로 바꾼다")
    @Test
    void updateRead() {
        //given
        StudylogRead studylogRead = new StudylogRead(judy, studylog);
        studylogReadRepository.save(studylogRead);

        List<Long> readIds = studylogService.findReadIds(judy.getId());
        SessionResponse sessionResponse = new SessionResponse(1L, "sessionResponse");
        List<StudylogResponse> studylogs = Arrays.asList(new StudylogResponse(studylog, sessionResponse, new MissionResponse(1L, "missionResponse", sessionResponse),
            new ArrayList<>(), false, 0L));

        //when
        studylogService.updateRead(studylogs, readIds);

        //then
        assertThat(studylogs.get(0).isRead()).isTrue();
    }

    @DisplayName("readRssFeeds() : 최신 studylog 50개 조회하고 studylogRssFeedResponse로 변환한다")
    @Test
    void readRssFeeds() {
        //given
        String url = "${application.url}";

        IntStream.rangeClosed(1, 100)
            .mapToObj(i -> new Studylog(judy, String.valueOf(i), String.valueOf(i), mission, new ArrayList<>()))
            .forEach(studylog -> studylogRepository.save(studylog));

        //when
        List<StudylogRssFeedResponse> studylogRssFeedResponses = studylogService.readRssFeeds(url);

        //then
        assertAll(
            () -> assertThat(studylogRssFeedResponses.get(0).getTitle()).isEqualTo("100"),
            () -> assertThat(studylogRssFeedResponses).hasSize(50));
    }
}
