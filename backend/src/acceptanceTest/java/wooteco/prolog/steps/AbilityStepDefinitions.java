package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.mapper.TypeRef;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.fixtures.AbilityFixture;
import wooteco.prolog.studylog.application.dto.ability.AbilityResponse;
import wooteco.prolog.studylog.application.dto.ability.ChildAbilityDto;
import wooteco.prolog.studylog.domain.ablity.Ability;

public class AbilityStepDefinitions extends AcceptanceSteps {

    private final List<Ability> savedAbilities = new ArrayList<>();

    @When("부모역량 {string}(을)(를) 추가하(면)(고)")
    public void 부모역량을주가하면(String abilityName) {
        String json = AbilityFixture.findByName(abilityName).toJson(null);

        context.invokeHttpPostWithToken("/abilities", json);
        List<AbilityResponse> responses = context.response.as(
            new TypeRef<List<AbilityResponse>>() {
            });

        addExtractedAbilityToSavedAbility(abilityName, responses);
    }

    @When("{string}의 자식역량 {string}(을)(를) 추가하(면)(고)")
    public void 자식역량을주가하면(String parentAbility, String childAbility) {
        Long parentAbilityId = findAbilityByName(parentAbility).getId();

        String json = AbilityFixture.findByName(childAbility).toJson(parentAbilityId);

        context.invokeHttpPostWithToken("/abilities", json);
        List<AbilityResponse> responses = context.response.as(
            new TypeRef<List<AbilityResponse>>() {
            });

        addExtractedAbilityToSavedAbility(childAbility, responses);
    }

    private Ability findAbilityByName(String abilityName) {
        return savedAbilities.stream()
            .filter(parentAbility -> parentAbility.getName().equals(abilityName))
            .findAny()
            .orElseGet(findAbilityByNameFromChildren(abilityName));
    }

    private Supplier<Ability> findAbilityByNameFromChildren(String abilityName) {
        return () -> savedAbilities.stream()
            .flatMap(parentAbility -> parentAbility.getChildren().stream())
            .filter(childAbility -> childAbility.getName().equals(abilityName))
            .findAny()
            .orElseThrow(IllegalArgumentException::new);
    }

    private void addExtractedAbilityToSavedAbility(String abilityName, List<AbilityResponse> abilityResponses) {
        for (AbilityResponse parent : abilityResponses) {
            if (parent.getName().equals(abilityName)) {
                Ability ability = AbilityFixture.findByName(abilityName)
                    .toAbility(parent.getId(), null);

                savedAbilities.add(ability);
            }

            for (ChildAbilityDto child : parent.getChildren()) {
                if (child.getName().equals(abilityName)) {
                    Ability childAbility = AbilityFixture.findByName(abilityName)
                        .toAbility(child.getId(), findAbilityByName(parent.getName()));
                    Ability parentAbility = findAbilityByName(parent.getName());
                    parentAbility.addChildAbility(childAbility);
                }
            }
        }
    }

    @Then("역량이 추가된다.")
    public void 역량이추가된다() {
        Response response = context.response;
        List<AbilityResponse> abilityResponses = response.as(
            new TypeRef<List<AbilityResponse>>() {
            });

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(abilityResponses)
            .usingRecursiveComparison()
            .isEqualTo(AbilityResponse.of(savedAbilities));
    }
}
