package wooteco.prolog.report.application.dto;

import org.springframework.stereotype.Component;
import wooteco.prolog.report.application.dto.Ability2.*;
import wooteco.prolog.report.domain.ablity.Ability2;
import wooteco.prolog.report.domain.ablity.History;
import wooteco.prolog.report.domain.ablity.StudylogsMappedToAbility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Component
public class HistoryDtoAssembler {

    private HistoryDtoAssembler() {
    }

    public History toHistory(HistoryRequest requests, Long memberId) {
        History history = new History(memberId);

        requests.getAbilities().stream()
                .map(this::toAbility)
                .forEach(history::addAbility);

        requests.getStudylogs()
                .forEach(studylog -> history.mappingStudylogAndAbilities(
                        studylog.getId(),
                        studylog.getAbilityNames()
                ));

        return history;
    }

    private Ability2 toAbility(AbilityUpdateRequest request) {
        List<Ability2> children = extractChildren(request);

        Ability2 ability = new Ability2(
                request.getId(),
                request.getName(),
                request.getDescription(),
                request.getColor(),
                new ArrayList<>()
        );

        children.forEach(ability::addChild);
        return ability;
    }

    private List<Ability2> extractChildren(AbilityUpdateRequest request) {
        List<Ability2> children = Collections.emptyList();

        if (Objects.nonNull(request.getChildren()) || request.getChildren().size() == 0) {
            children = request.getChildren().stream()
                    .map(this::toAbility)
                    .collect(toList());
        }

        return children;
    }

    public HistoryResponse toHistoryResponse(History history) {
        return new HistoryResponse(
                history.getId(),
                history.getCreatedAt(),
                toAbilityResponsesFromHistoryAbilities(history.getAbilities()),
                toStudylogAbilityMappingResponses(history.getStudylogsMappedToAbility())
        );
    }

    private List<StudylogAbilityMappingResponse> toStudylogAbilityMappingResponses(StudylogsMappedToAbility studylogsMappedToAbility) {
        return studylogsMappedToAbility.getIds().stream()
                .map(id -> new StudylogAbilityMappingResponse(id, toAbilityNames(studylogsMappedToAbility, id)))
                .collect(toList());
    }

    private List<String> toAbilityNames(StudylogsMappedToAbility studylogsMappedToAbility, Long id) {
        return studylogsMappedToAbility.getAbilitiesByStudylogId(id).stream()
                .map(Ability2::getName)
                .collect(toList());
    }

    private List<AbilityResponse> toAbilityResponsesFromHistoryAbilities(List<Ability2> historyAbilities) {
        return historyAbilities.stream()
                .map(this::toAbilityResponse)
                .collect(toList());
    }

    private List<AbilityResponse> toAbilityResponsesFromAbility(List<Ability2> abilities) {
        if (abilities.isEmpty()) {
            return Collections.emptyList();
        }

        return abilities.stream()
                .map(this::toAbilityResponse)
                .collect(toList());
    }

    private AbilityResponse toAbilityResponse(Ability2 ability) {
        return new AbilityResponse(
                ability.getId(),
                ability.getName(),
                ability.getDescription(),
                ability.getColor(),
                ability.isParent(),
                toAbilityResponsesFromAbility(ability.getChildren())
        );
    }
}
