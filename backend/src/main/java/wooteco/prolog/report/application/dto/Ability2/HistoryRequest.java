package wooteco.prolog.report.application.dto.Ability2;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class HistoryRequest {

    private List<AbilityUpdateRequest> abilities;
    private List<StudylogAbilityMappingRequest> studylogs;

    private HistoryRequest() {
    }
}
