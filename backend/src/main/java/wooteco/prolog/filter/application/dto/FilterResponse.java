package wooteco.prolog.filter.application.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.mission.application.dto.MissionResponse;
import wooteco.prolog.tag.dto.TagResponse;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class FilterResponse {
    private List<MissionResponse> missions;
    private List<TagResponse> tags;
}
