package wooteco.prolog.report.domain.ablity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Studylogs {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true, mappedBy = "history")
    private List<StudylogAbility> values;

    protected Studylogs() {
        this.values = new ArrayList<>();
    }

    public Studylogs(List<StudylogAbility> values) {
        this.values = values;
    }

    public void addAll(List<StudylogAbility> studylogAbilities) {
        values.addAll(studylogAbilities);
    }

    public List<StudylogAbility> getValues() {
        return values;
    }

    public StudylogsMappedToAbility getMapped() {
        return new StudylogsMappedToAbility(values);
    }
}
