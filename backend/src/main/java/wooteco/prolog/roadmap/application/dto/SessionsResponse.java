package wooteco.prolog.roadmap.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SessionsResponse {

    private List<SessionResponse> sessions;

    public SessionsResponse(final List<SessionResponse> sessions) {
        this.sessions = sessions;
    }
}
