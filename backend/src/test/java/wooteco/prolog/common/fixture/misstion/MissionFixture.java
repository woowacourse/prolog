package wooteco.prolog.common.fixture.misstion;

import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;

public class MissionFixture {

    public static Mission mission1(Session session) {
        return new Mission("mission1", session);
    }

    public static Mission mission2(Session session) {
        return new Mission("mission2", session);
    }

    public static Mission mission3(Session session) {
        return new Mission("mission3", session);
    }

}
