package wooteco.prolog.report.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.member.application.dto.MemberResponse;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FilterResponse {

    private List<LevelResponse> levels;
    private List<MissionResponse> missions;
    private List<TagResponse> tags;
    private List<MemberResponse> members;
}
