package wooteco.prolog.filter.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.level.application.dto.LevelResponse;
import wooteco.prolog.mission.application.dto.MissionResponse;
import wooteco.prolog.studylog.application.dto.TagResponse;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FilterResponse {

    private List<LevelResponse> levels;
    private List<MissionResponse> missions;
    private List<TagResponse> tags;
}
