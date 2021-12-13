package wooteco.prolog.studylog.studylog.domain.repository;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.studylog.domain.Level;
import wooteco.prolog.studylog.domain.Mission;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogRead;
import wooteco.prolog.studylog.domain.repository.*;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StudylogReadRepositoryTest {

    private static final Member 웨지 = new Member("sihyung92", "웨지", Role.CREW, 2222L,
            "https://avatars.githubusercontent.com/u/51393021?v=4");

    private static final Member 바다 = new Member("xrabcde", "바다", Role.CREW, 1111L,
            "https://avatars.githubusercontent.com/u/56033755?v=4");

    private Studylog studylog;
    private Member member;
    private Member bada;

    @Autowired
    private StudylogRepository studylogRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private StudylogReadRepository studylogReadRepository;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(웨지);
        bada = memberRepository.save(바다);
        Level level = levelRepository.save(new Level("레벨1"));
        Mission mission = missionRepository.save(new Mission("미션", level));
        studylog = studylogRepository
                .save(new Studylog(member, "제목", "내용", mission, Lists.emptyList()));
    }

    @DisplayName("멤버 아이디와 스터디로그 아이디로 조회한 적이 있다면 true, 없다면 false를 반환한다.")
    @Test
    void existsByMemberIdAndStudylogId() {
        //given
        StudylogRead studylogRead = new StudylogRead(bada, studylog);
        //when
        studylogReadRepository.save(studylogRead);
        boolean expectTrue = studylogReadRepository.existsByMemberIdAndStudylogId(bada.getId(), studylog.getId());
        boolean expectFalse = studylogReadRepository.existsByMemberIdAndStudylogId(member.getId(), studylog.getId());
        //then
        assertThat(expectTrue).isTrue();
        assertThat(expectFalse).isFalse();
    }

    @DisplayName("멤버 아이디로 조회한 글목록을 가져온다.")
    @Test
    void findByMemberId() {
        //given
        StudylogRead studylogRead = new StudylogRead(bada, studylog);
        studylogReadRepository.save(studylogRead);
        List<StudylogRead> expected = Collections.singletonList(studylogRead);
        //when
        List<StudylogRead> actual = studylogReadRepository.findByMemberId(bada.getId());
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
