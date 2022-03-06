package wooteco.prolog.session.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.session.domain.Session;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SessionResponse {

    private Long id;
    private String name;

    public static SessionResponse of(Session session) {
        return new SessionResponse(session.getId(), session.getName());
    }
}
