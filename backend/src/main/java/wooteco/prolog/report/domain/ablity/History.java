package wooteco.prolog.report.domain.ablity;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static wooteco.prolog.report.domain.ablity.domain.AbilityValidator.validateDuplicateAbilityColor;
import static wooteco.prolog.report.domain.ablity.domain.AbilityValidator.validateDuplicateAbilityName;

@Entity(name = "abilities_history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<HistoryAbility> abilities;

    protected History() {
        this(new ArrayList<>());
    }

    public History(List<HistoryAbility> abilities) {
        this(null, null, abilities);
    }

    public History(Long id, LocalDateTime createdAt, List<HistoryAbility> abilities) {
        this.id = id;
        this.createdAt = createdAt;
        this.abilities = abilities;
    }

    public void add(Ability2 ability) {
        List<Ability2> abilities = extractAbilities();
        validateDuplicateAbilityName(ability, abilities);
        validateDuplicateAbilityColor(ability, abilities);

        this.abilities.add(new HistoryAbility(this, ability));
    }

    private List<Ability2> extractAbilities() {
        return abilities.stream()
                .map(HistoryAbility::getAbility)
                .collect(toList());
    }
}
