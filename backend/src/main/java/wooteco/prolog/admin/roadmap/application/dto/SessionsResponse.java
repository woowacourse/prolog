package wooteco.prolog.admin.roadmap.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.admin.roadmap.application.dto.SessionResponse;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SessionsResponse {

    private List<SessionResponse> sessions;

    public SessionsResponse(final List<SessionResponse> sessions) {
        this.sessions = sessions;
    }
}
