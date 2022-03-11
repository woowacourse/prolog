package wooteco.prolog.report.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportAbilityRequest {

    private Long abilityId;
    private Float weight;

}
