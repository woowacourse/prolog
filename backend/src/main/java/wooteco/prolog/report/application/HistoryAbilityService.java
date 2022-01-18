package wooteco.prolog.report.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.report.application.dto.Ability2.HistoryRequest;
import wooteco.prolog.report.application.dto.Ability2.HistoryResponse;
import wooteco.prolog.report.application.dto.HistoryDtoAssembler;
import wooteco.prolog.report.domain.ablity.Ability2;
import wooteco.prolog.report.domain.ablity.History;
import wooteco.prolog.report.domain.ablity.repository.HistoryRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class HistoryAbilityService {

    private final HistoryRepository historyRepository;
    private final HistoryDtoAssembler historyDtoAssembler;
    private final EntityManager entityManager;

    @Transactional
    public HistoryResponse update(HistoryRequest requests) {
        History history = historyDtoAssembler.toHistory(requests);
        historyRepository.findFirstByOrderByCreatedAtDesc()
                .ifPresent(savedHistory -> dirtyCheck(savedHistory, history));

        History savedHistory = entityManager.merge(history);

        return historyDtoAssembler.toHistoryResponse(savedHistory);
    }

    private void dirtyCheck(History savedHistory, History history) {
        Map<Long, Ability2> originAbilities = savedHistory.getAbilities().stream()
                .collect(toMap(Ability2::getId, Function.identity()));

        List<Ability2> sourceAbilities = history.getAbilities().stream()
                .filter(ability -> Objects.nonNull(ability.getId()))
                .collect(toList());

        boolean hasChanged = sourceAbilities.stream()
                .anyMatch(ability -> isFieldChangedAbility(ability, originAbilities));

        if(hasChanged) {
            throw new IllegalArgumentException("역량데이터는 변경될 수 없습니다.");
        }
    }

    private boolean isFieldChangedAbility(Ability2 ability, Map<Long, Ability2> originAbilities) {
        Ability2 savedAbility = originAbilities.getOrDefault(ability.getId(), null);

        if(Objects.isNull(savedAbility)) {
            return false;
        }

        return !savedAbility.isExactlyEquals(ability);
    }
}
