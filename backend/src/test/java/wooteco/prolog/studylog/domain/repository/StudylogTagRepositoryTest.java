package wooteco.prolog.studylog.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.prolog.session.domain.repository.MissionRepository;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogTag;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.support.utils.RepositoryTest;

@RepositoryTest
class StudylogTagRepositoryTest {

    private static final Member 웨지 = new Member("sihyung92", "웨지", Role.CREW, 2222L,
        "https://avatars.githubusercontent.com/u/51393021?v=4");

    @Autowired
    private StudylogTagRepository studylogTagRepository;

    @Autowired
    private StudylogRepository studylogRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private TagRepository tagRepository;

    @DisplayName("StudylogTag 생성")
    @Test
    void createStudylogTag() {
        // given
        Member member = memberRepository.save(웨지);
        Session session = sessionRepository.save(new Session("세션1"));
        Mission mission = missionRepository.save(new Mission("미션", session));
        Tag tag = tagRepository.save(new Tag("태그"));
        Studylog studylog = studylogRepository.save(new Studylog(member, "제목", "내용", mission, Lists.emptyList()));

        // when
        StudylogTag studylogTag = new StudylogTag(studylog, tag);
        StudylogTag savedStudylogTag = studylogTagRepository.save(studylogTag);

        // then
        assertThat(savedStudylogTag.getId()).isNotNull();
        assertThat(savedStudylogTag).usingRecursiveComparison()
            .ignoringFields("id", "createdAt", "updatedAt")
            .isEqualTo(studylogTag);
    }

    @DisplayName("Tag 리스트와 매칭되는 StudylogTag 리스트 조회")
    @Test
    void findByTagIn() {
        // given
        Member member = memberRepository.save(웨지);
        Session session = sessionRepository.save(new Session("세션1"));
        Mission mission = missionRepository.save(new Mission("미션", session));
        Studylog studylog1 = studylogRepository
            .save(new Studylog(member, "제목1", "내용1", mission, Lists.emptyList()));
        Studylog studylog2 = studylogRepository
            .save(new Studylog(member, "제목2", "내용2", mission, Lists.emptyList()));

        Tag tag1 = tagRepository.save(new Tag("태그1"));
        Tag tag2 = tagRepository.save(new Tag("태그2"));

        StudylogTag studylogTag1 = studylogTagRepository.save(new StudylogTag(studylog1, tag1));
        StudylogTag studylogTag2 = studylogTagRepository.save(new StudylogTag(studylog1, tag2));
        StudylogTag studylogTag3 = studylogTagRepository.save(new StudylogTag(studylog2, tag2));

        // when
        List<StudylogTag> studylogTags = studylogTagRepository.findByTagIn(Arrays.asList(tag1, tag2));

        // then
        assertThat(studylogTags).usingFieldByFieldElementComparator()
            .containsExactlyInAnyOrder(studylogTag1, studylogTag2, studylogTag3);
    }

    @DisplayName("Tag 리스트와 매칭되는 studylogTag가 없을시 빈 리스트 조회")
    @Test
    void findByTagInEmpty() {
        // given
        Tag tag1 = tagRepository.save(new Tag("태그1"));
        Tag tag2 = tagRepository.save(new Tag("태그2"));

        // when
        List<StudylogTag> studylogTags = studylogTagRepository.findByTagIn(Arrays.asList(tag1, tag2));

        // then
        assertThat(studylogTags).isEmpty();
    }
}
