package wooteco.prolog.common.fixture.misstion;

import wooteco.prolog.session.application.dto.MissionRequest;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;

public enum MissionFixture {

    로또_미션("로또 미션"), 자동차_미션("자동차 미션");

    private final String missionName;

    MissionFixture(final String missionName) {
        this.missionName = missionName;
    }

    public MissionRequest asRequest(long sessionId) {
        return new MissionRequest(missionName, sessionId);
    }
}
