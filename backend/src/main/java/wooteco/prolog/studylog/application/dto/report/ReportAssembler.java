package wooteco.prolog.studylog.application.dto.report;

import static java.util.stream.Collectors.toList;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.application.dto.report.request.ReportRequest;
import wooteco.prolog.studylog.application.dto.report.request.abilitigraph.AbilityRequest;
import wooteco.prolog.studylog.application.dto.report.request.abilitigraph.GraphRequest;
import wooteco.prolog.studylog.application.dto.report.request.studylog.StudylogRequest;
import wooteco.prolog.studylog.application.dto.report.response.ReportResponse;
import wooteco.prolog.studylog.application.dto.report.response.abilityGraph.GraphAbilityResponse;
import wooteco.prolog.studylog.application.dto.report.response.abilityGraph.GraphResponse;
import wooteco.prolog.studylog.application.dto.report.response.studylogs.StudylogAbilityResponse;
import wooteco.prolog.studylog.application.dto.report.response.studylogs.StudylogResponse;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.ablity.Ability;
import wooteco.prolog.studylog.domain.report.Report;
import wooteco.prolog.studylog.domain.report.abilitygraph.AbilityGraph;
import wooteco.prolog.studylog.domain.report.abilitygraph.ReportedAbilities;
import wooteco.prolog.studylog.domain.report.abilitygraph.ReportedAbility;
import wooteco.prolog.studylog.domain.report.abilitygraph.datastructure.GraphAbility;
import wooteco.prolog.studylog.domain.report.studylog.ReportedStudylog;
import wooteco.prolog.studylog.domain.report.studylog.ReportedStudylogAbility;
import wooteco.prolog.studylog.domain.report.studylog.ReportedStudylogs;

@Component
public class ReportAssembler {

    @PersistenceContext
    private EntityManager entityManager;

    public ReportAssembler(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Report of(ReportRequest reportRequest, Member member) {
        List<ReportedStudylog> reportedStudylogs = reportRequest.getStudylogs().stream()
            .map(this::of)
            .collect(toList());

        return new Report(
            reportRequest.getId(),
            reportRequest.getTitle(),
            reportRequest.getDescription(),
            of(reportRequest.getAbilityGraph()),
            new ReportedStudylogs(reportedStudylogs),
            reportRequest.isRepresent(),
            member
        );
    }

    private AbilityGraph of(GraphRequest graphRequest) {
        List<ReportedAbility> reportedAbilities = graphRequest.getAbilities().stream()
            .map(this::of)
            .collect(toList());

        return new AbilityGraph(new ReportedAbilities(reportedAbilities));
    }

    private ReportedAbility of(AbilityRequest abilityRequest) {
        return new ReportedAbility(
            findAbilityById(abilityRequest.getId()),
            abilityRequest.getWeight(),
            abilityRequest.isRepresent()
        );
    }

    private ReportedStudylog of(StudylogRequest studylogRequest) {
        List<ReportedStudylogAbility> abilities = studylogRequest.getAbilities().stream()
            .map(this::findAbilityById)
            .map(ReportedStudylogAbility::new)
            .collect(toList());

        return new ReportedStudylog(
            findStudylogById(studylogRequest.getId()),
            abilities
        );
    }

    private Studylog findStudylogById(Long id) {
        return entityManager.getReference(Studylog.class, id);
    }

    private Ability findAbilityById(Long id) {
        return entityManager.getReference(Ability.class, id);
    }

    public
    ReportResponse of(Report report) {
        List<StudylogResponse> studylogResponses = report.getStudylogs().stream()
            .map(this::of)
            .collect(toList());

        return new ReportResponse(
            report.getId(),
            report.getTitle(),
            report.getDescription(),
            of(report.getAbilityGraph()),
            studylogResponses,
            report.isRepresent()
        );
    }

    private StudylogResponse of(ReportedStudylog reportedStudylog) {
        List<StudylogAbilityResponse> abilityResponses = reportedStudylog.getAbilities().stream()
            .map(this::of)
            .collect(toList());

        return new StudylogResponse(
            reportedStudylog.getId(),
            reportedStudylog.getCreatedAt(),
            reportedStudylog.getUpdatedAt(),
            reportedStudylog.getTitle(),
            abilityResponses
        );
    }

    private StudylogAbilityResponse of(ReportedStudylogAbility reportedStudylogAbility) {
        return new StudylogAbilityResponse(
            reportedStudylogAbility.getId(),
            reportedStudylogAbility.getName(),
            reportedStudylogAbility.getColor(),
            reportedStudylogAbility.isParent()
        );
    };

    private GraphResponse of(AbilityGraph abilityGraph) {
        List<GraphAbilityResponse> graphAbilityRespons = abilityGraph.getAbilities().stream()
            .map(this::of)
            .collect(toList());

        return new GraphResponse(graphAbilityRespons);
    }

    private GraphAbilityResponse of(GraphAbility graphAbility) {
        return new GraphAbilityResponse(
            graphAbility.getId(),
            graphAbility.getName(),
            graphAbility.getWeight(),
            graphAbility.getPercentage(),
            graphAbility.isPresent()
        );
    }

}
