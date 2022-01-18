package wooteco.prolog.report.domain.ablity;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Embeddable
public class Studylogs {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<StudylogAbility> values;

    protected Studylogs() {
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
