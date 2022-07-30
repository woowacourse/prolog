package wooteco.prolog.badge.domain;

import java.util.Arrays;
import java.util.List;

public enum FourthCrewSessions {

    LEVEL_TWO(10L, 11L),
    LEVEL_THREE(12L);

    private final List<Long> sessions;

    FourthCrewSessions(Long... sessions) {
        this.sessions = Arrays.asList(sessions);
    }

    public List<Long> getSessions() {
        return sessions;
    }
}
