package wooteco.prolog.common.fixture.level;

import wooteco.prolog.session.domain.Level;

public class LevelFixture {

    public static Level level1() {
        return new Level("level1");
    }

    public static Level level2() {
        return new Level("level2");
    }

    public static Level level3() {
        return new Level("level3");
    }
}
