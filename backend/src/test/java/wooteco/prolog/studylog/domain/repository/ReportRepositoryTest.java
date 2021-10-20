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
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import wooteco.prolog.common.fixture.ability.AbilityFixture;
import wooteco.prolog.common.fixture.level.LevelFixture;
import wooteco.prolog.common.fixture.member.MemberFixture;
import wooteco.prolog.common.fixture.misstion.MissionFixture;
import wooteco.prolog.common.fixture.report.ReportFixture;
import wooteco.prolog.common.fixture.studylog.StudylogFixture;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.report.application.dto.report.ReportAssembler;
import wooteco.prolog.report.domain.ablity.repository.AbilityRepository;
import wooteco.prolog.report.domain.report.repository.ReportRepository;
import wooteco.prolog.studylog.domain.Level;
import wooteco.prolog.studylog.domain.Mission;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.report.domain.ablity.Ability;
import wooteco.prolog.report.domain.report.Report;
import wooteco.prolog.report.domain.report.abilitygraph.AbilityGraph;
import wooteco.prolog.report.domain.report.abilitygraph.GraphAbilities;
import wooteco.prolog.report.domain.report.abilitygraph.GraphAbility;
import wooteco.prolog.report.domain.report.studylog.ReportedStudylog;
import wooteco.prolog.report.domain.report.studylog.ReportedStudylogAbility;
import wooteco.prolog.report.domain.report.studylog.ReportedStudylogs;

@DataJpaTest
@Import(ReportAssembler.class)
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

    @Autowired
    private ReportAssembler reportAssembler;

    @Test
    void findReportByMember() throws JsonProcessingException {
        Member member = memberRepository.save(MemberFixture.crewMember1());
        Report report = createReport(member);
        reportRepository.save(report);

        flushAndClear();

        PageRequest pageable = PageRequest.of(0, 10);
        List<Report> reports = reportRepository.findReportsByMember(member, pageable).toList();
        Report report1 = reports.get(0);

        assertThat(reportAssembler.of(report1))
            .usingRecursiveComparison()
            .ignoringFieldsMatchingRegexes(".*id", ".*createAt", ".*updateAt", ".*createdAt", ".*updatedAt")
            .isEqualTo(reportAssembler.of(createReport(member)));

        System.out.println(new ObjectMapper().writeValueAsString(reportAssembler.of(report)));
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
        Ability ability1 = abilityRepository.save(AbilityFixture.parentAbility1(member));
        Ability ability2 = abilityRepository.save(AbilityFixture.parentAbility2(member));
        Ability ability3 = abilityRepository.save(AbilityFixture.parentAbility3(member));
        GraphAbilities graphAbilities = new GraphAbilities(Arrays.asList(
            new GraphAbility(ability1, 1L, true),
            new GraphAbility(ability2, 2L, true),
            new GraphAbility(ability3, 3L, true)
        ));

        Level level1 = levelRepository.save(LevelFixture.level1());
        Mission mission = missionRepository.save(MissionFixture.mission1(level1));
        Ability ability4 = abilityRepository.save(AbilityFixture.childAbility1(member, ability1));
        Ability ability5 = abilityRepository.save(AbilityFixture.childAbility2(member, ability2));
        Studylog studylog = studylogRepository.save(createStudyLog(member, mission));
        ReportedStudylogs reportedStudylogs = new ReportedStudylogs(Arrays.asList(
            new ReportedStudylog(
                studylog.getId(),
                Arrays.asList(
                    new ReportedStudylogAbility(ability4),
                    new ReportedStudylogAbility(ability5)
                )
            ),
            new ReportedStudylog(
                studylog.getId(),
                Arrays.asList(
                    new ReportedStudylogAbility(ability4),
                    new ReportedStudylogAbility(ability5)
                )
            )
        ));

        return ReportFixture.builder()
            .member(member)
            .title("test report")
            .description("test report")
            .graph(new AbilityGraph(graphAbilities))
            .member(member)
            .studylogs(reportedStudylogs)
            .build();
    }

    private void flushAndClear() {
        testEntityManager.flush();
        testEntityManager.clear();
    }
}
