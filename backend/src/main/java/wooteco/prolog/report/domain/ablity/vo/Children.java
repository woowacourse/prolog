package wooteco.prolog.report.domain.ablity.vo;

import wooteco.prolog.report.domain.ablity.Ability2;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Embeddable
public class Children {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true, mappedBy = "parent")
    private List<Ability2> values;

    protected Children() {
    }

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
