package wooteco.prolog.studylog.studylog.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.session.domain.Level;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.repository.LevelRepository;
import wooteco.prolog.session.domain.repository.MissionRepository;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogScrap;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.domain.repository.StudylogScrapRepository;
import wooteco.support.utils.RepositoryTest;

@RepositoryTest
public class StudylogScrapRepositoryTest {

    private static final Member 웨지 = new Member("sihyung92", "웨지", Role.CREW, 2222L,
        "https://avatars.githubusercontent.com/u/51393021?v=4");

    private Studylog studylog;
    private Member member;

    @Autowired
    private StudylogRepository studylogRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private StudylogScrapRepository studylogScrapRepository;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(웨지);
        Level level = levelRepository.save(new Level("레벨1"));
        Mission mission = missionRepository.save(new Mission("미션", level));
        studylog = studylogRepository
            .save(new Studylog(member, "제목", "내용", mission, Lists.emptyList()));
    }

    @DisplayName("count기능을 테스트한다.")
    @Test
    void countMemberScrapTest() {
        //given
        StudylogScrap studylogScrap = new StudylogScrap(member, studylog);
        //when
        int countBefore = studylogScrapRepository
            .countByMemberIdAndScrapStudylogId(member.getId(), studylog.getId());

        studylogScrapRepository.save(studylogScrap);

        int countAfter = studylogScrapRepository
            .countByMemberIdAndScrapStudylogId(member.getId(), studylog.getId());
        //then
        assertThat(countBefore).isEqualTo(0);
        assertThat(countAfter).isEqualTo(1);
    }

    @DisplayName("멤버 아이디와 스터디로그 아이디로 찾아오기 기능을 테스트한다.")
    @Test
    void findByMemberIdAndStudylogIdTest() {
        //given
        StudylogScrap expectedStudylogScrap = new StudylogScrap(member, studylog);
        //when
        studylogScrapRepository.save(expectedStudylogScrap);
        StudylogScrap studylogScrap = studylogScrapRepository
            .findByMemberIdAndStudylogId(member.getId(), studylog.getId()).get();

        //then
        assertThat(expectedStudylogScrap).isEqualTo(studylogScrap);
    }
}
