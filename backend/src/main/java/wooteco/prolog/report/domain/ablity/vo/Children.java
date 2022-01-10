package wooteco.prolog.report.domain.ablity.vo;

import wooteco.prolog.report.domain.ablity.Ability2;

import java.util.List;

public class Children {

    private final List<Ability2> values;

    public Children(List<Ability2> values) {
        this.values = values;
    }

    public void add(Ability2 ability) {
        this.values.add(ability);
    }

    public List<Ability2> getValues() {
        return values;
    }
}
