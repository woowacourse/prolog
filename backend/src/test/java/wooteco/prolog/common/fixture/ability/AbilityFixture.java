package wooteco.prolog.common.fixture.ability;

import wooteco.prolog.studylog.domain.ablity.Ability;

public class AbilityFixture {

    public static Ability parentAbility1() {
        return Ability
            .parent("test parent ability1", "test parent ability description1", "test color1");
    }

    public static Ability parentAbility2() {
        return Ability
            .parent("test parent ability2", "test parent ability description2", "test color2");
    }

    public static Ability parentAbility3() {
        return Ability
            .parent("test parent ability3", "test parent ability description3", "test color3");
    }

    public static Ability childAbility1() {
        return Ability
            .parent("test child ability1", "test child ability description1", "test color1");
    }

    public static Ability childAbility2() {
        return Ability
            .parent("test child ability2", "test child ability description2", "test color2");
    }

    public static Ability childAbility3() {
        return Ability
            .parent("test child ability3", "test child ability description3", "test color3");
    }
}
