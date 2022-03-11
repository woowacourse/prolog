package wooteco.prolog.report.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.prolog.report.domain.Report;
import wooteco.prolog.report.domain.ReportAbility;
import wooteco.prolog.report.domain.ReportAbilityStudylog;

@AllArgsConstructor
@Getter
public class ReportResponse {

    private Long id;
    private String title;
    private String description;
    private List<ReportAbilityResponse> abilities;

    public static ReportResponse of(Report report, List<ReportAbility> reportAbilities, List<ReportAbilityStudylog> reportAbilityStudylogs) {

        List<ReportAbilityResponse> reportAbilityResponses = ReportAbilityResponse.listOf(reportAbilities, reportAbilityStudylogs);

        return new ReportResponse(report.getId(), report.getTitle(), report.getDescription(), reportAbilityResponses);
    }
}
