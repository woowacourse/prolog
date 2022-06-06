package wooteco.prolog.report.application.dto;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import wooteco.prolog.report.domain.Report;
import wooteco.prolog.report.domain.ReportAbility;
import wooteco.prolog.report.domain.ReportStudylog;

@AllArgsConstructor
@Getter
public class ReportResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<ReportAbilityResponse> abilities;
    private List<ReportStudylogResponse> studylogs;

    public static ReportResponse of(Report report, List<ReportAbility> reportAbilities,
                                    List<ReportStudylog> reportStudylogs) {
        List<ReportAbilityResponse> abilityResponses = ReportAbilityResponse.listOf(
            reportAbilities);
        List<ReportStudylogResponse> studylogResponses = ReportStudylogResponse.listOf(
            reportStudylogs);

        return new ReportResponse(report.getId(), report.getTitle(), report.getDescription(),
            report.getStartDate(), report.getEndDate(), abilityResponses, studylogResponses);
    }

    public static ReportResponse of(Report report) {
        return new ReportResponse(report.getId(), report.getTitle(), report.getDescription(),
            report.getStartDate(), report.getEndDate(), Collections.emptyList(),
            Collections.emptyList());
    }

    public static List<ReportResponse> listOf(Page<Report> reports) {
        return reports.stream()
            .map(ReportResponse::of)
            .collect(Collectors.toList());
    }
}
