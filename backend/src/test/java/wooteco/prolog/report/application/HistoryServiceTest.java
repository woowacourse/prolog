package wooteco.prolog.report.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wooteco.prolog.report.application.dto.Ability2.*;
import wooteco.prolog.report.domain.ablity.repository.HistoryRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HistoryServiceTest {

    @Autowired
    private HistoryAbilityService historyAbilityService;

    @Autowired
    private HistoryRepository historyRepository;

    private List<AbilityUpdateRequest> abilityUpdateRequests;

    private List<StudylogAbilityMappingRequest> studylogAbilityMappingRequests;

    private HistoryRequest historyRequest;

    @BeforeEach
    void setUp() {
        abilityUpdateRequests = Arrays.asList(
                new AbilityUpdateRequest(
                        null,
                        "부모역량1",
                        "부모역량 입니다.",
                        "1234",
                        Collections.singletonList(
                                new AbilityUpdateRequest(
                                        null,
                                        "자식역량1",
                                        "자식역량 입니다.",
                                        "1234",
                                        Collections.emptyList()
                                )
                        )
                ),
                new AbilityUpdateRequest(
                        null,
                        "부모역량2",
                        "부모역량 입니다.",
                        "12345",
                        Collections.singletonList(
                                new AbilityUpdateRequest(
                                        null,
                                        "자식역량2",
                                        "자식역량 입니다.",
                                        "12345",
                                        Collections.emptyList()
                                )
                        )
                )
        );

        studylogAbilityMappingRequests = Arrays.asList(
                new StudylogAbilityMappingRequest(
                        1L,
                        Arrays.asList("자식역량1", "자식역량2")
                )
        );

        historyRequest = new HistoryRequest(
                abilityUpdateRequests,
                studylogAbilityMappingRequests
        );
    }

    @DisplayName("역량 이력을 저장한다.")
    @Test
    void update() {
        HistoryResponse expected = historyAbilityService.update(historyRequest);

        assertThat(expected.getAbilities())
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*id", ".*createdAt")
                .isEqualTo(abilityUpdateRequests);
    }

    @DisplayName("역량 이력을 여러번 저장하면, 여러번 저장된다.")
    @Test
    void update_multi() {
        HistoryResponse expected1 = historyAbilityService.update(historyRequest);
        HistoryResponse expected2 = historyAbilityService.update(responseToRequest(expected1));

        assertThat(expected1)
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes("id", "createAt")
                .isEqualTo(expected2);
    }

    @DisplayName("역량 이력을 한번 저장하고, 특정 역량을 변경하고 또 저장하면 이전 역량 히스토리의 역량은 영향을 받지 않는다.")
    @Test
    void update3() {

    }

    private HistoryRequest responseToRequest(HistoryResponse historyResponse) {
        return new HistoryRequest(
                toAbilityUpdateRequests(historyResponse.getAbilities()),
                toStudylogAbilityMappingRequests(historyResponse.getStudylogs())
        );
    }

    private List<StudylogAbilityMappingRequest> toStudylogAbilityMappingRequests(List<StudylogAbilityMappingResponse> studylogs) {
        return studylogs.stream()
                .map(this::toStudylogAbilityMappingRequest)
                .collect(toList());
    }

    private StudylogAbilityMappingRequest toStudylogAbilityMappingRequest(StudylogAbilityMappingResponse studylogAbilityMappingResponse) {
        return new StudylogAbilityMappingRequest(
                studylogAbilityMappingResponse.getId(),
                studylogAbilityMappingResponse.getAbilityNames()
        );
    }

    private List<AbilityUpdateRequest> toAbilityUpdateRequests(List<AbilityResponse> abilityResponses) {
        if(abilityResponses.isEmpty()) {
            return Collections.emptyList();
        }

        return abilityResponses.stream()
                .map(this::toAbilityUpdateRequest)
                .collect(toList());
    }

    private AbilityUpdateRequest toAbilityUpdateRequest(AbilityResponse abilityResponse) {
        return new AbilityUpdateRequest(
                abilityResponse.getId(),
                abilityResponse.getName(),
                abilityResponse.getDescription(),
                abilityResponse.getColor(),
                toAbilityUpdateRequests(abilityResponse.getChildren())
        );
    }
}
