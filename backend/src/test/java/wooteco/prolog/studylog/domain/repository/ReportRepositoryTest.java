package wooteco.prolog.studylog.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import wooteco.prolog.common.fixture.ability.AbilityFixture;
import wooteco.prolog.common.fixture.level.LevelFixture;
import wooteco.prolog.common.fixture.member.MemberFixture;
import wooteco.prolog.common.fixture.misstion.MissionFixture;
import wooteco.prolog.common.fixture.report.ReportFixture;
import wooteco.prolog.common.fixture.studylog.StudylogFixture;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.studylog.domain.Level;
import wooteco.prolog.studylog.domain.Mission;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.report.Report;
import wooteco.prolog.studylog.domain.report.ReportedAbility;

@DataJpaTest
class ReportRepositoryTest {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private StudylogRepository studylogRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    void findReportByMember() {
        Member member = MemberFixture.crewMember1();
        memberRepository.save(member);

        Level level = LevelFixture.level1();
        levelRepository.save(level);

        Mission mission = MissionFixture.mission1(level);
        missionRepository.save(mission);

        Studylog studylog = createStudyLog(member, mission);
        studylogRepository.save(studylog);

        Report report = createReport(member, studylog);
        reportRepository.save(report);

        flushAndClear();

        PageRequest pageable = PageRequest.of(0, 10);

        List<Report> reports = reportRepository.findReportsByMember(member, pageable);
        assertThat(reports).hasSize(1);
    }

    private Studylog createStudyLog(Member member, Mission mission) {
        return StudylogFixture.builder()
            .mission(mission)
            .title("title")
            .content("content")
            .member(member)
            .build();
    }

    private Report createReport(Member member, Studylog studylog) {
        return ReportFixture.builder()
            .member(member)
            .title("test report")
            .description("test report")
            .reportedAbilities(new ReportedAbility(studylog, AbilityFixture.parentAbility1()))
            .reportedAbilities(new ReportedAbility(studylog, AbilityFixture.parentAbility2()))
            .member(member)
            .studylog(studylog)
            .build();
    }

    private void flushAndClear() {
        testEntityManager.flush();
        testEntityManager.clear();
    }
}
