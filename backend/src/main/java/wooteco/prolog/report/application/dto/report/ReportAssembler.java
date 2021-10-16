package wooteco.prolog.report.application.dto.report;

import static java.util.stream.Collectors.toList;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.report.application.dto.report.request.ReportRequest;
import wooteco.prolog.report.application.dto.report.request.abilitigraph.AbilityRequest;
import wooteco.prolog.report.application.dto.report.request.abilitigraph.GraphRequest;
import wooteco.prolog.report.application.dto.report.request.studylog.ReportStudylogRequest;
import wooteco.prolog.report.application.dto.report.response.ReportResponse;
import wooteco.prolog.report.application.dto.report.response.SimpleReportResponse;
import wooteco.prolog.report.application.dto.report.response.ability_graph.GraphAbilityResponse;
import wooteco.prolog.report.application.dto.report.response.ability_graph.GraphResponse;
import wooteco.prolog.report.application.dto.report.response.studylogs.StudylogAbilityResponse;
import wooteco.prolog.report.application.dto.report.response.studylogs.StudylogResponse;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.report.domain.ablity.Ability;
import wooteco.prolog.report.domain.report.Report;
import wooteco.prolog.report.domain.report.abilitygraph.AbilityGraph;
import wooteco.prolog.report.domain.report.abilitygraph.GraphAbilities;
import wooteco.prolog.report.domain.report.abilitygraph.GraphAbility;
import wooteco.prolog.report.domain.report.abilitygraph.datastructure.GraphAbilityDto;
import wooteco.prolog.report.domain.report.studylog.ReportedStudylog;
import wooteco.prolog.report.domain.report.studylog.ReportedStudylogAbility;
import wooteco.prolog.report.domain.report.studylog.ReportedStudylogs;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;

@Component
public class ReportAssembler {

    @PersistenceContext
    private EntityManager entityManager;
    private final StudylogRepository studylogRepository;

    public ReportAssembler(EntityManager entityManager, StudylogRepository studylogRepository) {
        this.entityManager = entityManager;
        this.studylogRepository = studylogRepository;
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
        List<GraphAbility> reportedAbilities = graphRequest.getAbilities().stream()
            .map(this::of)
            .collect(toList());

        return new AbilityGraph(new GraphAbilities(reportedAbilities));
    }

    private GraphAbility of(AbilityRequest abilityRequest) {
        return new GraphAbility(
            findAbilityById(abilityRequest.getId()),
            abilityRequest.getWeight(),
            abilityRequest.isRepresent()
        );
    }

    private ReportedStudylog of(ReportStudylogRequest reportStudylogRequest) {
        List<ReportedStudylogAbility> abilities = reportStudylogRequest.getAbilities().stream()
            .map(this::findAbilityById)
            .map(ReportedStudylogAbility::new)
            .collect(toList());

        return new ReportedStudylog(
            reportStudylogRequest.getId(),
            abilities
        );
    }

    private Ability findAbilityById(Long id) {
        return entityManager.getReference(Ability.class, id);
    }

    public ReportResponse of(Report report) {
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

        Studylog studylog = studylogRepository.findById(reportedStudylog.getStudylogId())
                .orElseThrow(IllegalArgumentException::new);

        return new StudylogResponse(
            studylog.getId(),
            studylog.getCreatedAt(),
            studylog.getUpdatedAt(),
            studylog.getTitle(),
            abilityResponses
        );
    }

    private StudylogAbilityResponse of(ReportedStudylogAbility reportedStudylogAbility) {
        return new StudylogAbilityResponse(
            reportedStudylogAbility.getAbility().getId(),
            reportedStudylogAbility.getName(),
            reportedStudylogAbility.getColor(),
            reportedStudylogAbility.isParent()
        );
    }

    ;

    private GraphResponse of(AbilityGraph abilityGraph) {
        List<GraphAbilityResponse> graphAbilityRespons = abilityGraph.getAbilities().stream()
            .map(this::of)
            .collect(toList());

        return new GraphResponse(graphAbilityRespons);
    }

    private GraphAbilityResponse of(GraphAbilityDto graphAbilityDto) {
        return new GraphAbilityResponse(
            graphAbilityDto.getId(),
            graphAbilityDto.getName(),
            graphAbilityDto.getColor(),
            graphAbilityDto.getWeight(),
            graphAbilityDto.getPercentage(),
            graphAbilityDto.isPresent()
        );
    }

    public SimpleReportResponse simpleOf(Report report) {
        return new SimpleReportResponse(
            report.getId(),
            report.getTitle(),
            report.isRepresent()
        );
    }
}
