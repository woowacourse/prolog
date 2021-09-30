package wooteco.prolog.report.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FilterResponse {

    private List<LevelResponse> levels;
    private List<MissionResponse> missions;
    private List<TagResponse> tags;
}
