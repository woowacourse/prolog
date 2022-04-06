package wooteco.prolog.report.application.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.prolog.report.domain.ReportAbility;

@AllArgsConstructor
@Getter
public class ReportAbilityResponse {

    private Long id;
    private String name;
    private String description;
    private String color;
    private Integer weight;
    private Long originAbilityId;

    public static List<ReportAbilityResponse> listOf(List<ReportAbility> reportAbilities) {
        return reportAbilities.stream()
            .map(ReportAbilityResponse::of)
            .collect(Collectors.toList());
    }

    public static ReportAbilityResponse of(ReportAbility reportAbility) {
        return new ReportAbilityResponse(
            reportAbility.getId(),
            reportAbility.getName(),
            reportAbility.getDescription(),
            reportAbility.getColor(),
            reportAbility.getWeight(),
            reportAbility.getOriginAbilityId()
        );
    }
}
