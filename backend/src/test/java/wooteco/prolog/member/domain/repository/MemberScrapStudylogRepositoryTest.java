package wooteco.prolog.member.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.MemberScrapStudylog;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.studylog.domain.Level;
import wooteco.prolog.studylog.domain.Mission;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.LevelRepository;
import wooteco.prolog.studylog.domain.repository.MissionRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;

@DataJpaTest
public class MemberScrapStudylogRepositoryTest {

    private static final Member 웨지 = new Member("sihyung92", "웨지", Role.CREW, 2222L,
        "https://avatars.githubusercontent.com/u/51393021?v=4");

    private Studylog studylog;

    @Autowired
    private StudylogRepository studylogRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private MemberScrapStudylogRepository memberScrapStudylogRepository;

    @BeforeEach
    void setUp() {
        Member member = memberRepository.save(웨지);
        Level level = levelRepository.save(new Level("레벨1"));
        Mission mission = missionRepository.save(new Mission("미션", level));
        studylog = studylogRepository
            .save(new Studylog(member, "제목", "내용", mission, Lists.emptyList()));
    }

    @DisplayName("count기능을 테스트한다.")
    @Test
    void countMemberScrapTest() {
        //given
        MemberScrapStudylog memberScrapStudylog = new MemberScrapStudylog(웨지, studylog);
        //when
        int countBefore = memberScrapStudylogRepository
            .countByMemberIdAndScrapStudylogId(웨지.getId(), studylog.getId());

        memberScrapStudylogRepository.save(memberScrapStudylog);

        int countAfter = memberScrapStudylogRepository
            .countByMemberIdAndScrapStudylogId(웨지.getId(), studylog.getId());
        //then
        assertThat(countBefore).isEqualTo(0);
        assertThat(countAfter).isEqualTo(1);
    }

    @DisplayName("멤버 아이디와 스터디로그 아이디로 찾아오기 기능을 테스트한다.")
    @Test
    void findByMemberIdAndStudylogIdTest() {
        //given
        MemberScrapStudylog expectedMemberScrapStudylog = new MemberScrapStudylog(웨지, studylog);
        //when
        memberScrapStudylogRepository.save(expectedMemberScrapStudylog);
        MemberScrapStudylog memberScrapStudylog = memberScrapStudylogRepository
            .findByMemberIdAndScrapStudylogId(웨지.getId(), studylog.getId()).get();

        //then
        assertThat(expectedMemberScrapStudylog).isEqualTo(memberScrapStudylog);
    }
}
