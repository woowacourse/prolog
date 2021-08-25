package wooteco.prolog.studylog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FilterResponse {

    private List<LevelResponse> levels;
    private List<MissionResponse> missions;
    private List<TagResponse> tags;
}
