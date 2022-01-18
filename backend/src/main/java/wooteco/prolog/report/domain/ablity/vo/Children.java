package wooteco.prolog.report.domain.ablity.vo;

import wooteco.prolog.report.domain.ablity.Ability2;
import wooteco.prolog.report.domain.ablity.AbilityChild;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Embeddable
public class Children {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<AbilityChild> values;

    protected Children() {
        this.values = new ArrayList<>();
    }

    public Children(List<AbilityChild> values) {
        this.values = values;
    }

    public static Children of(Ability2 source, List<Ability2> abilities) {
        List<AbilityChild> abilityChildren = abilities.stream()
                .map(ability -> new AbilityChild(source, ability))
                .collect(toList());

        return new Children(abilityChildren);
    }

    public void add(Ability2 target, Ability2 source) {
        this.values.add(new AbilityChild(target, source));
    }

    public List<Ability2> getValues() {
        return values.stream()
                .map(AbilityChild::getChild)
                .collect(toList());
    }
}
