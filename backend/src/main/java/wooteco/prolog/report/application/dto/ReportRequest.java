package wooteco.prolog.report.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequest {

    private String title;
    private String description;
    private List<ReportAbilityRequest> reportAbility;
}
