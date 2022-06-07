package wooteco.prolog.report.application.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.prolog.report.domain.ReportStudylog;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.domain.Studylog;

@AllArgsConstructor
@Getter
public class ReportStudylogResponse {

    private StudylogResponse studylog;
    private List<ReportAbilityResponse> abilities;
    private List<ReportStudylogAbilityResponse> studylogAbilities;

    public static List<ReportStudylogResponse> listOf(List<ReportStudylog> reportStudylogs) {
        return reportStudylogs.stream()
            .collect(Collectors.groupingBy(ReportStudylog::getStudylog))
            .entrySet().stream()
            .map(it -> ReportStudylogResponse.of(it.getKey(), it.getValue()))
            .collect(Collectors.toList());
    }

    public static ReportStudylogResponse of(Studylog studylog,
                                            List<ReportStudylog> reportStudylogs) {
        StudylogResponse studylogResponse = StudylogResponse.of(studylog);
        List<ReportAbilityResponse> abilityResponses = reportStudylogs.stream()
            .map(it -> ReportAbilityResponse.of(it.getReportAbility()))
            .collect(Collectors.toList());
        List<ReportStudylogAbilityResponse> studylogAbilityResponses = reportStudylogs.stream()
            .map(it -> ReportStudylogAbilityResponse.of(it.getStudylogAbility()))
            .collect(Collectors.toList());

        return new ReportStudylogResponse(studylogResponse, abilityResponses,
            studylogAbilityResponses);
    }

}
