package wooteco.prolog.badge.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum FourthCrewSessions {

    LEVEL_TWO(10L, 11L),
    LEVEL_THREE(12L);

    private final List<Long> sessions;

    FourthCrewSessions(Long... sessions) {
        validateSessionCount(sessions);
        this.sessions = Arrays.asList(sessions);
    }

    private void validateSessionCount(Long[] sessions) {
        if (Objects.isNull(sessions) || isInvalidLength(sessions)) {
            throw new IllegalArgumentException("세션은 최소 1이상 존재해야 합니다.");
        }
    }

    private boolean isInvalidLength(Long[] sessions) {
        return sessions.length < 1;
    }

    public List<Long> getSessions() {
        return sessions;
    }
}
