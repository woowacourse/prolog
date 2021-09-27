package wooteco.prolog.common.fixture.misstion;

import wooteco.prolog.studylog.domain.Level;
import wooteco.prolog.studylog.domain.Mission;

public class MissionFixture {

    public static Mission mission1(Level level) {
        return new Mission("mission1", level);
    }

    public static Mission mission2(Level level) {
        return new Mission("mission2", level);
    }

    public static Mission mission3(Level level) {
        return new Mission("mission3", level);
    }

}
