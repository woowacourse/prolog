package wooteco.prolog.common.fixture.ability;

import wooteco.prolog.ability.domain.Ability;
import wooteco.prolog.member.domain.Member;

public class AbilityFixture {

    public static Ability parentAbility1(Member member) {
        return Ability
            .parent("test parent ability1", "test parent ability description1", "test color1",
                    member);
    }

    public static Ability parentAbility2(Member member) {
        return Ability
            .parent("test parent ability2", "test parent ability description2", "test color2",
                    member);
    }

    public static Ability parentAbility3(Member member) {
        return Ability
            .parent("test parent ability3", "test parent ability description3", "test color3",
                    member);
    }

    public static Ability childAbility1(Member member, Ability ability) {
        return Ability
            .child("test child ability1", "test child ability description1", "test color1", ability,
                   member);
    }

    public static Ability childAbility2(Member member, Ability ability) {
        return Ability
            .child("test child ability2", "test child ability description2", "test color2", ability,
                   member);
    }

    public static Ability childAbility3(Member member, Ability ability) {
        return Ability
            .child("test child ability3", "test child ability description3", "test color3", ability,
                   member);
    }
}
