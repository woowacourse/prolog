package wooteco.prolog.report.domain.ablity;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

public class StudylogsMappedToAbility {

    private final Map<Long, List<Ability2>> values;

    public StudylogsMappedToAbility(List<StudylogAbility> studylogAbilities) {
        this(toValues(studylogAbilities));
    }

    private static Map<Long, List<Ability2>> toValues(List<StudylogAbility> studylogAbilities) {
        return studylogAbilities.stream()
                .collect(groupingBy(StudylogAbility::getStudylogId))
                .entrySet().stream()
                .collect(toMap(Map.Entry::getKey, toAbilities()));
    }

    private static Function<Map.Entry<Long, List<StudylogAbility>>, List<Ability2>> toAbilities() {
        return e -> e.getValue().stream()
                .map(StudylogAbility::getAbility)
                .collect(toList());
    }

    public StudylogsMappedToAbility(Map<Long, List<Ability2>> values) {
        this.values = values;
    }

    public Set<Long> getIds() {
        return values.keySet();
    }

    public List<Ability2> getAbilitiesByStudylogId(Long id) {
        return values.getOrDefault(id, Collections.emptyList());
    }
}
