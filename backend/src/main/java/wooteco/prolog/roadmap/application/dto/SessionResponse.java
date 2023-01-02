package wooteco.prolog.roadmap.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.session.domain.Session;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SessionResponse {

    private Long sessionId;
    private String name;

    public SessionResponse(final Long sessionId, final String name) {
        this.sessionId = sessionId;
        this.name = name;
    }

    public static SessionResponse createResponse(final Session session) {
        return new SessionResponse(session.getId(), session.getName());
    }
}
