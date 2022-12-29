package wooteco.prolog.session.application.dto;

import wooteco.prolog.session.domain.Session;

public class SessionRequest {

    private String name;

    public SessionRequest() {
    }

    public SessionRequest(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Session toEntity() {
        return new Session(this.name);
    }
}
