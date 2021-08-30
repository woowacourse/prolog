package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.mapper.TypeRef;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

        Ability ability = extractAbility(abilityName, responses);
        savedAbilities.add(ability);
    }

    @When("{string}의 자식역량 {string}(을)(를) 추가하(면)(고)")
    public void 자식역량을주가하면(String parentAbility, String childAbility) {
        Long parentAbilityId = findAbilityIdByName(parentAbility);
        if(Objects.isNull(parentAbilityId)) {
            throw new AssertionError();
        }

        String json = AbilityFixture.findByName(childAbility).toJson(parentAbilityId);

        context.invokeHttpPostWithToken("/abilities", json);
        List<AbilityResponse> responses = context.response.as(
            new TypeRef<List<AbilityResponse>>() {
            });

        Ability ability = extractAbility(childAbility, responses);
        savedAbilities.add(ability);
    }

    private Long findAbilityIdByName(String abilityName) {
        return savedAbilities.stream()
            .filter(ability -> ability.getName().equals(abilityName))
            .findAny()
            .map(Ability::getId)
            .orElse(null);
    }

    private Ability extractAbility(String abilityName, List<AbilityResponse> abilityResponses) {
        for (AbilityResponse parent : abilityResponses) {
            if(parent.getName().equals(abilityName)) {
                return AbilityFixture.findByName(abilityName).toAbility(parent.getId(), null);
            }

            for (ChildAbilityDto child : parent.getChildren()) {
                if(child.getName().equals(abilityName)) {
                    return AbilityFixture.findByName(abilityName).toAbility(child.getId(), parent.getId());
                }
            }
        }

        throw new AssertionError();
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
            .isEqualTo(AbilityResponse.from(savedAbilities));
    }
}
