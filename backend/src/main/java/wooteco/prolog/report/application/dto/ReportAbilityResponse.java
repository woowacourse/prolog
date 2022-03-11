package wooteco.prolog.report.application.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.prolog.report.domain.ReportAbility;
import wooteco.prolog.report.domain.ReportAbilityStudylog;
import wooteco.prolog.studylog.application.dto.StudylogResponse;

@AllArgsConstructor
@Getter
public class ReportAbilityResponse {

    private Integer weight;
    private String name;
    private String description;
    private String color;
    private ReportAbilityResponse parent;
    private List<StudylogResponse> studylogs;

    public static ReportAbilityResponse of(ReportAbility reportAbility, List<ReportAbilityStudylog> reportAbilityStudylogs) {
        List<StudylogResponse> studylogs = reportAbilityStudylogs.stream()
            .map(it -> StudylogResponse.of(it.getStudylog()))
            .collect(Collectors.toList());

        return new ReportAbilityResponse(
            reportAbility.getWeight(),
            reportAbility.getName(),
            reportAbility.getDescription(),
            reportAbility.getColor(),
            reportAbility.getParent() == null ? null : ReportAbilityResponse.of(reportAbility.getParent(), Collections.emptyList()),
            studylogs);
    }

    public static List<ReportAbilityResponse> listOf(List<ReportAbility> reportAbilities, List<ReportAbilityStudylog> reportAbilityStudylogs) {
        return reportAbilities.stream()
            .map(it -> createReportAbilityResponse(it, reportAbilityStudylogs))
            .collect(Collectors.toList());
    }

    private static ReportAbilityResponse createReportAbilityResponse(ReportAbility reportAbility, List<ReportAbilityStudylog> persistReportAbilityStudylogs) {
        List<ReportAbilityStudylog> reportAbilityStudylogs = persistReportAbilityStudylogs.stream()
            .filter(it -> reportAbility == it.getReportAbility())
            .collect(Collectors.toList());
        return ReportAbilityResponse.of(reportAbility, reportAbilityStudylogs);
    }
}
