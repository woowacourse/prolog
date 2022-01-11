package wooteco.prolog.report.domain.ablity.domain;

import wooteco.prolog.report.domain.ablity.Ability2;
import wooteco.prolog.report.domain.ablity.vo.Color;
import wooteco.prolog.report.domain.ablity.vo.Name;
import wooteco.prolog.report.exception.AbilityParentChildColorDifferentException;
import wooteco.prolog.studylog.exception.AbilityNameDuplicateException;
import wooteco.prolog.studylog.exception.AbilityParentColorDuplicateException;

import java.util.List;

public class AbilityValidator {

    private AbilityValidator() {
    }

    public static void validateDuplicateAbilityName(Ability2 target, List<Ability2> abilities) {
        boolean hasSameName = abilities.stream()
                .anyMatch(ability -> ability.isSameName(target));

        if (hasSameName) {
            throw new AbilityNameDuplicateException();
        }
    }

    public static void validateDuplicateAbilityColor(Ability2 target, List<Ability2> abilities) {
        boolean hasSameColor = abilities.stream()
                .anyMatch(ability -> ability.isSameColor(target));

        if (hasSameColor) {
            throw new AbilityParentColorDuplicateException();
        }
    }

    public static void validateSameColor(Color sourceColor, Color targetColor) {
        if (!sourceColor.equals(targetColor)) {
            throw new AbilityParentChildColorDifferentException();
        }
    }

    public static void validateSameName(Name sourceName, Name targetName) {
        if (sourceName.equals(targetName)) {
            throw new AbilityNameDuplicateException();
        }
    }
}
