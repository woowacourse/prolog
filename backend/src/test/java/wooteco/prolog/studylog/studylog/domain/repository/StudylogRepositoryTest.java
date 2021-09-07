package wooteco.prolog.studylog.studylog.domain.repository;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.studylog.domain.Level;
import wooteco.prolog.studylog.domain.Mission;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogTag;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.prolog.studylog.domain.repository.LevelRepository;
import wooteco.prolog.studylog.domain.repository.MissionRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.domain.repository.StudylogSpecification;
import wooteco.prolog.studylog.domain.repository.StudylogTagRepository;
import wooteco.prolog.studylog.domain.repository.TagRepository;

@DataJpaTest
class StudylogRepositoryTest {

    private static final String STUDYLOG1_TITLE = "이것은 제목";
    private static final String STUDYLOG2_TITLE = "이것은 두번째 제목";
    private static final String STUDYLOG3_TITLE = "이것은 3 제목";
    private static final String STUDYLOG4_TITLE = "이것은 네번 제목";
    private final Member member1 = new Member("이름1", "별명1", Role.CREW, 1L, "image");
    private final Member member2 = new Member("이름2", "별명2", Role.CREW, 2L, "image");
    private final Level level1 = new Level("레벨1");
    private final Level level2 = new Level("레벨2");
    private final Mission mission1 = new Mission("자동차 미션", level1);
    private final Mission mission2 = new Mission("수동차 미션", level2);
    private final Studylog studylog4 = new Studylog(member2, STUDYLOG4_TITLE, "포모의 스터디로그", mission2,
                                                    asList());
    private final Tag tag1 = new Tag("소롱의글쓰기");
    private final Tag tag2 = new Tag("스프링");
    private final Studylog studylog1 = new Studylog(member1, STUDYLOG1_TITLE, "피케이와 포모의 스터디로그",
                                                    mission1, asList(tag1, tag2));
    private final Tag tag3 = new Tag("감자튀기기");
    private final Studylog studylog2 = new Studylog(member1, STUDYLOG2_TITLE, "피케이와 포모의 스터디로그 2",
                                                    mission1, asList(tag2, tag3));
    private final Tag tag4 = new Tag("집필왕웨지");
    private final Tag tag5 = new Tag("피케이");
    private final Studylog studylog3 = new Studylog(member2, STUDYLOG3_TITLE, "피케이 스터디로그", mission2,
                                                    asList(tag3, tag4, tag5));
    private final List<Tag> tags = asList(
        tag1, tag2, tag3, tag4, tag5
    );
    @Autowired
    private StudylogRepository studylogRepository;
    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private MissionRepository missionRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private StudylogTagRepository studylogTagRepository;

    @BeforeEach
    void setUp() {
        levelRepository.saveAll(asList(level1, level2));

        missionRepository.saveAll(asList(mission1, mission2));

        memberRepository.saveAll(asList(member1, member2));

        tagRepository.saveAll(tags);

        studylogRepository.saveAll(asList(studylog1, studylog2, studylog3, studylog4));
    }

    @DisplayName("레벨로 찾기")
    @Test
    void findWithLevel() {
        //given
        List<Long> levelIds = singletonList(level1.getId());
        Specification<Studylog> specs = StudylogSpecification.findByLevelIn(levelIds)
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
        List<Long> tagIds = studylogTags.stream().map(it -> it.getTag().getId())
            .collect(Collectors.toList());
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

    @DisplayName("레벨, 미션, 태그로 찾기")
    @Test
    void findWithLevelAndMissionAndTag() {
        //given
        List<Long> levelIds = singletonList(level1.getId());
        List<Long> missionIds = singletonList(mission1.getId());
        List<StudylogTag> studylogTags = studylogTagRepository.findByTagIn(asList(tag1, tag2));
        List<Long> tagIds = studylogTags.stream().map(it -> it.getTag().getId())
            .collect(Collectors.toList());
        Specification<Studylog> specs = StudylogSpecification.findByLevelIn(levelIds)
            .and(StudylogSpecification.equalIn("mission", missionIds))
            .and(StudylogSpecification.findByTagIn(tagIds))
            .and(StudylogSpecification.distinct(true));
        // when
        Page<Studylog> studylogs = studylogRepository.findAll(specs, PageRequest.of(0, 10));

        assertThat(studylogs).containsExactlyInAnyOrder(studylog1, studylog2);
    }

    @DisplayName("멤버로 스터디로그를 찾아오는지 테스트")
    @Test
    void findByMemberTest() {
        //given
        //when
        Page<Studylog> expectedResult = studylogRepository
            .findByMember(member1, PageRequest.of(0, 10));
        //then
        assertThat(expectedResult.getContent()).containsExactlyInAnyOrder(studylog1, studylog2);
    }
}
