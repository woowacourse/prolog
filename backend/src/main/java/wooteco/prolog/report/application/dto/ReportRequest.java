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

    public int findWeight(Long activityId) {
        return reportAbility.stream()
            .filter(it -> it.getAbilityId() == activityId)
            .map(it -> it.getWeight())
            .findAny()
            .orElseThrow(() -> new RuntimeException("찾는 역량이 없습니다."));
    }
}
