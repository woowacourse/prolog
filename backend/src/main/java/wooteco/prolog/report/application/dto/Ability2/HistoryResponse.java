package wooteco.prolog.report.application.dto.Ability2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import wooteco.prolog.report.application.dto.report.response.studylogs.StudylogAbilityResponse;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class HistoryResponse {

    private final Long id;
    private final LocalDateTime createAt;
    private final List<AbilityResponse> abilities;
    private final List<StudylogAbilityMappingResponse> studylogs;
}
