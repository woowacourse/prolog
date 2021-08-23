package wooteco.prolog.filter.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.mission.application.dto.MissionResponse;
import wooteco.prolog.tag.dto.TagResponse;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FilterResponse {

    private List<MissionResponse> missions;
    private List<TagResponse> tags;
}
