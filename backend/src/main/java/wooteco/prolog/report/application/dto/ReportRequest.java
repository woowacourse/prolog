package wooteco.prolog.report.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReportRequest {

    private String title;
    private String description;
    private String startDate;
    private String endDate;
    private List<ReportAbilityRequest> reportAbility;

    public int findWeight(Long activityId) {
        return reportAbility.stream()
            .filter(it -> it.getAbilityId().equals(activityId))
            .map(ReportAbilityRequest::getWeight)
            .findAny()
            .orElseThrow(() -> new RuntimeException("찾는 역량이 없습니다."));
    }
}

