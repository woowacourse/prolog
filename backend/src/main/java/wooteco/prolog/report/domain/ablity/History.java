package wooteco.prolog.report.domain.ablity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@EntityListeners(AuditingEntityListener.class)
@Entity(name = "abilities_history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;

    @Embedded
    private Abilities abilities;

    @Embedded
    private Studylogs studylogs;

    public History() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    public History(List<HistoryAbility> abilities, List<StudylogAbility> studylogs) {
        this(null, null, abilities, new Studylogs(studylogs));
    }

    public History(Long id, LocalDateTime createdAt, List<HistoryAbility> abilities, Studylogs studylogs) {
        this.id = id;
        this.createdAt = createdAt;
        this.abilities = new Abilities(abilities);
        this.studylogs = studylogs;
    }

    public void addAbility(Ability2 ability) {
        this.abilities.add(new HistoryAbility(this, ability));
    }

    public void mappingStudylogAndAbilities(Long studylogId, List<String> abilityNames) {
        List<StudylogAbility> studylogAbilities = abilityNames.stream()
                .distinct()
                .map(abilities::findAbilityByName)
                .map(ability -> new StudylogAbility(null, this, ability, studylogId))
                .collect(toList());

        studylogs.addAll(studylogAbilities);
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Ability2> getAbilities() {
        return abilities.getValues();
    }

    public StudylogsMappedToAbility getStudylogsMappedToAbility() {
        return studylogs.getMapped();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        History history = (History) o;
        return Objects.equals(id, history.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
