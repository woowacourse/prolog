package wooteco.prolog.session.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.session.domain.Session;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SessionResponse {

    private Long id;
    private String name;

    public static SessionResponse of(Session session) {
        if (session == null) {
            return null;
        }
        return new SessionResponse(session.getId(), session.getName());
    }
}
