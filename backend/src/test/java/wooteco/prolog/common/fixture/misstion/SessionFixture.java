package wooteco.prolog.common.fixture.misstion;

import wooteco.prolog.session.application.dto.SessionRequest;

public enum SessionFixture {

    임파시블_세션("임파시블세션");

    private final String sessionName;

    SessionFixture(final String sessionName) {
        this.sessionName = sessionName;
    }

    public SessionRequest asRequest() {
        return new SessionRequest(sessionName);
    }
}
