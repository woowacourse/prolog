package wooteco.prolog.studylog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.session.application.dto.SessionResponse;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FilterResponse {

    private List<SessionResponse> sessions;
    private List<MissionResponse> missions;
}
