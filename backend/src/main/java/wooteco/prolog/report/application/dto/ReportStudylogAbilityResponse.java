package wooteco.prolog.report.application.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.prolog.report.domain.ReportStudylogAbility;

@AllArgsConstructor
@Getter
public class ReportStudylogAbilityResponse {

    private String name;
    private String color;

    public static List<ReportStudylogAbilityResponse> listOf(
        List<ReportStudylogAbility> reportStudylogAbilities) {
        return reportStudylogAbilities.stream()
            .map(ReportStudylogAbilityResponse::of)
            .collect(Collectors.toList());
    }

    public static ReportStudylogAbilityResponse of(ReportStudylogAbility reportStudylogAbility) {
        return new ReportStudylogAbilityResponse(
            reportStudylogAbility.getName(),
            reportStudylogAbility.getColor()
        );
    }
}
