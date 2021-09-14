package wooteco.prolog.studylog.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
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
import wooteco.prolog.studylog.application.dto.report.ReportResponse;
import wooteco.prolog.studylog.domain.Level;
import wooteco.prolog.studylog.domain.Mission;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.ablity.Ability;
import wooteco.prolog.studylog.domain.report.Report;
import wooteco.prolog.studylog.domain.report.abilitygraph.Graph;
import wooteco.prolog.studylog.domain.report.abilitygraph.ReportedAbilities;
import wooteco.prolog.studylog.domain.report.abilitygraph.ReportedAbility;
import wooteco.prolog.studylog.domain.report.studylog.ReportedStudylog;
import wooteco.prolog.studylog.domain.report.studylog.ReportedStudylogAbility;
import wooteco.prolog.studylog.domain.report.studylog.ReportedStudylogs;

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

    @Autowired
    private AbilityRepository abilityRepository;

    @Test
    void findReportByMember() throws JsonProcessingException {
        Member member = memberRepository.save(MemberFixture.crewMember1());
        Report report = createReport(member);
        reportRepository.save(report);

        flushAndClear();

        PageRequest pageable = PageRequest.of(0, 10);
        List<Report> reports = reportRepository.findReportsByMember(member, pageable);
        Report report1 = reports.get(0);

        assertThat(ReportResponse.from(report1))
            .usingRecursiveComparison()
            .ignoringFieldsMatchingRegexes(".*id", ".*createAt", ".*updateAt")
            .isEqualTo(ReportResponse.from(createReport(member)));

        System.out.println(new ObjectMapper().writeValueAsString(ReportResponse.from(report)));
    }

    private Studylog createStudyLog(Member member, Mission mission) {
        return StudylogFixture.builder()
            .mission(mission)
            .title("title")
            .content("content")
            .member(member)
            .build();
    }

    private Report createReport(Member member) {
        Ability ability1 = abilityRepository.save(AbilityFixture.parentAbility1());
        Ability ability2 = abilityRepository.save(AbilityFixture.parentAbility2());
        Ability ability3 = abilityRepository.save(AbilityFixture.parentAbility3());
        ReportedAbilities reportedAbilities = new ReportedAbilities(Arrays.asList(
            new ReportedAbility(ability1, 1L),
            new ReportedAbility(ability2, 2L),
            new ReportedAbility(ability3, 3L)
        ));

        Level level1 = levelRepository.save(LevelFixture.level1());
        Mission mission = missionRepository.save(MissionFixture.mission1(level1));
        Ability ability4 = abilityRepository.save(AbilityFixture.childAbility1());
        Ability ability5 = abilityRepository.save(AbilityFixture.childAbility2());
        Studylog studylog = studylogRepository.save(createStudyLog(member, mission));
        ReportedStudylogs reportedStudylogs = new ReportedStudylogs(Arrays.asList(
            new ReportedStudylog(
                studylog,
                Arrays.asList(
                    new ReportedStudylogAbility(ability4, true),
                    new ReportedStudylogAbility(ability5, true)
                )
            ),
            new ReportedStudylog(
                studylog,
                Arrays.asList(
                    new ReportedStudylogAbility(ability4, true),
                    new ReportedStudylogAbility(ability5, true)
                )
            )
        ));

        return ReportFixture.builder()
            .member(member)
            .title("test report")
            .description("test report")
            .graph(new Graph(reportedAbilities))
            .member(member)
            .studylogs(reportedStudylogs)
            .build();
    }

    private void flushAndClear() {
        testEntityManager.flush();
        testEntityManager.clear();
    }
}
