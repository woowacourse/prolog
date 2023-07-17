package wooteco.prolog.studylog.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.session.application.dto.SessionResponse;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FilterResponse {

    private List<SessionResponse> sessions;
    private List<MissionResponse> missions;
    private List<TagResponse> tags;
    private List<MemberResponse> members;
}
