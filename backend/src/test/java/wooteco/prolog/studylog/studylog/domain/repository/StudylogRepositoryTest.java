package wooteco.prolog.studylog.studylog.domain.repository;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.MissionRepository;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogTag;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.domain.repository.StudylogSpecification;
import wooteco.prolog.studylog.domain.repository.StudylogTagRepository;
import wooteco.prolog.studylog.domain.repository.TagRepository;
import wooteco.support.utils.RepositoryTest;

@RepositoryTest
class StudylogRepositoryTest {

    private static final String STUDYLOG1_TITLE = "이것은 제목";
    private static final String STUDYLOG2_TITLE = "이것은 두번째 제목";
    private static final String STUDYLOG3_TITLE = "이것은 3 제목";
    private static final String STUDYLOG4_TITLE = "이것은 네번 제목";

    @Autowired
    private StudylogRepository studylogRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private MissionRepository missionRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private StudylogTagRepository studylogTagRepository;

    private final Member member1 = new Member("이름1", "별명1", Role.CREW, 1L, "image");
    private final Member member2 = new Member("이름2", "별명2", Role.CREW, 2L, "image");

    private final Session session1 = new Session("세션1");
    private final Session session2 = new Session("세션2");

    private final Mission mission1 = new Mission("자동차 미션", session1);
    private final Mission mission2 = new Mission("수동차 미션", session2);

    private final Tag tag1 = new Tag("소롱의글쓰기");
    private final Tag tag2 = new Tag("스프링");
    private final Tag tag3 = new Tag("감자튀기기");
    private final Tag tag4 = new Tag("집필왕웨지");
    private final Tag tag5 = new Tag("피케이");
    private final List<Tag> tags = asList(
        tag1, tag2, tag3, tag4, tag5
    );

    private final Studylog studylog1 = new Studylog(member1, STUDYLOG1_TITLE, "피케이와 포모의 스터디로그", session1, mission1,
        asList(tag1, tag2));
    private final Studylog studylog2 = new Studylog(member1, STUDYLOG2_TITLE, "피케이와 포모의 스터디로그 2", session1, mission1,
        asList(tag2, tag3));
    private final Studylog studylog3 = new Studylog(member2, STUDYLOG3_TITLE, "피케이 스터디로그", session2, mission2,
        asList(tag3, tag4, tag5));
    private final Studylog studylog4 = new Studylog(member2, STUDYLOG4_TITLE, "포모의 스터디로그", session2, mission2,
        List.of());

    @BeforeEach
    void setUp() {
        sessionRepository.saveAll(asList(session1, session2));

        missionRepository.saveAll(asList(mission1, mission2));

        memberRepository.saveAll(asList(member1, member2));

        tagRepository.saveAll(tags);

        studylogRepository.saveAll(asList(studylog1, studylog2, studylog3, studylog4));
    }

    @DisplayName("세션로 찾기")
    @Test
    void findWithSession() {
        //given
        List<Long> sessionIds = singletonList(session1.getId());
        Specification<Studylog> specs = StudylogSpecification.equalIn("session", sessionIds)
            .and(StudylogSpecification.distinct(true));

        // when
        Page<Studylog> studylogs = studylogRepository.findAll(specs, PageRequest.of(0, 10));

        //then
        assertThat(studylogs).containsExactlyInAnyOrder(studylog1, studylog2);
    }

    @DisplayName("미션으로 찾기")
    @Test
    void findWithMission() {
        //given
        List<Long> missionIds = singletonList(mission2.getId());
        Specification<Studylog> specs = StudylogSpecification.equalIn("mission", missionIds)
            .and(StudylogSpecification.distinct(true));

        // when
        Page<Studylog> studylogs = studylogRepository.findAll(specs, PageRequest.of(0, 10));

        //then
        assertThat(studylogs).containsExactlyInAnyOrder(studylog3, studylog4);
    }

    @DisplayName("태그로 찾기")
    @Test
    void findWithTags() {
        // given
        List<StudylogTag> studylogTags = studylogTagRepository.findByTagIn(asList(tag1, tag2));
        List<Long> tagIds = studylogTags.stream().map(it -> it.getTag().getId()).collect(toList());
        Specification<Studylog> specs = StudylogSpecification.findByTagIn(tagIds)
            .and(StudylogSpecification.distinct(true));

        // when
        Page<Studylog> studylogs = studylogRepository.findAll(specs, PageRequest.of(0, 10));

        //then
        assertThat(studylogs).containsExactlyInAnyOrder(studylog1, studylog2);
    }

    @DisplayName("멤버로 찾기")
    @Test
    void findWithMembers() {
        // given
        List<String> usernames = asList(member1.getUsername(), member2.getUsername());
        Specification<Studylog> specs = StudylogSpecification.findByUsernameIn(usernames)
            .and(StudylogSpecification.distinct(true));

        // when
        Page<Studylog> studylogs = studylogRepository.findAll(specs, PageRequest.of(0, 10));

        //then
        assertThat(studylogs).containsExactlyInAnyOrder(studylog1, studylog2, studylog3, studylog4);
    }

    @DisplayName("세션, 미션, 태그로 찾기")
    @Test
    void findWithSessionAndMissionAndTag() {
        //given
        List<Long> sessionIds = singletonList(session1.getId());
        List<Long> missionIds = singletonList(mission1.getId());
        List<StudylogTag> studylogTags = studylogTagRepository.findByTagIn(asList(tag1, tag2));
        List<Long> tagIds = studylogTags.stream().map(it -> it.getTag().getId()).collect(toList());

        Specification<Studylog> specs = StudylogSpecification.equalIn("session", sessionIds)
            .and(StudylogSpecification.equalIn("mission", missionIds))
            .and(StudylogSpecification.findByTagIn(tagIds))
            .and(StudylogSpecification.distinct(true));

        // when
        Page<Studylog> studylogs = studylogRepository.findAll(specs, PageRequest.of(0, 10));

        assertThat(studylogs).containsExactlyInAnyOrder(studylog1, studylog2);
    }

    @DisplayName("멤버로 스터디로그를 찾아오는지 테스트")
    @Test
    @Transactional
    void findByMemberTest() {
        //given
        //when
        Page<Studylog> expectedResult = studylogRepository
            .findByMember(member1, PageRequest.of(0, 10));
        //then
        assertThat(expectedResult.getContent()).containsExactlyInAnyOrder(studylog1, studylog2);
    }

    @DisplayName("주어진 날짜시간 이후의 글 목록을 가져온다.")
    @Test
    void findByPastDateAndSize() throws InterruptedException {
        // given
        TimeUnit.SECONDS.sleep(2); // 2초간 정지

        Studylog studylog = studylogRepository.save(new Studylog(
            member1,
            "새로운 글",
            "새로운 글이얌",
            mission1,
            asList(tag1, tag2)
        ));

        // when
        LocalDateTime localDateTime = LocalDateTime.now().minusSeconds(1);
        List<Studylog> studylogs = studylogRepository.findByPastDays(localDateTime);

        // then
        assertThat(studylogs).hasSize(1);
        assertThat(studylogs).containsExactly(studylog);
    }

    @DisplayName("스터디로그 중 최신 50개만 조회한다.")
    @Test
    void findTop50OrderByCreatedAtDesc() {
        // given
        List<String> titles = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            Studylog studylog = new Studylog(
                member1,
                String.valueOf(i),
                String.valueOf(i),
                mission1,
                asList(tag1, tag2)
            );
            Studylog savedStudylog = studylogRepository.save(studylog);

            titles.add(savedStudylog.getTitle());
        }

        // when
        List<Studylog> studylogs = studylogRepository.findTop50ByDeletedFalseOrderByIdDesc();

        // then
        assertThat(studylogs).hasSize(50);
    }
}
