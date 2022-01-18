package wooteco.prolog.report.domain.ablity;

import wooteco.prolog.report.domain.ablity.vo.Name;
import wooteco.prolog.report.exception.AbilityNotFoundException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static wooteco.prolog.report.domain.ablity.domain.AbilityValidator.validateDuplicateAbilityColor;
import static wooteco.prolog.report.domain.ablity.domain.AbilityValidator.validateDuplicateAbilityName;

@Embeddable
public class Abilities {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true, mappedBy = "history")
    private List<HistoryAbility> values;

    protected Abilities() {
        this.values = new ArrayList<>();
    }

    public Abilities(List<HistoryAbility> abilities) {
        this.values = abilities;
    }

    public List<Ability2> getValues() {
        return values.stream()
                .map(HistoryAbility::getAbility)
                .collect(toList());
    }

    public Ability2 findAbilityByName(String abilityName) {
        List<Ability2> abilities = this.values.stream()
                .map(HistoryAbility::getAbility)
                .collect(toList());

        return recursive(abilities, abilityName)
                .orElseThrow(AbilityNotFoundException::new);
    }

    private Optional<Ability2> recursive(List<Ability2> abilities, String abilityName) {
        if (abilities.isEmpty()) {
            return Optional.empty();
        }

        for (Ability2 ability : abilities) {
            if (ability.isSameName(new Name(abilityName))) {
                return Optional.of(ability);
            }
        }

        List<Ability2> children = abilities.stream()
                .flatMap(ability -> ability.getChildren().stream())
                .collect(toList());

        return recursive(children, abilityName);
    }

    public void add(HistoryAbility historyAbility) {
        validateDuplicateAbilityName(historyAbility.getAbility(), getValues());
        validateDuplicateAbilityColor(historyAbility.getAbility(), getValues());

        this.values.add(historyAbility);
    }
}
