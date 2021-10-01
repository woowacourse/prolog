package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.common.exception.BadRequestCode;
import wooteco.prolog.common.exception.ExceptionDto;
import wooteco.prolog.fixtures.AbilityAcceptanceFixture;
import wooteco.prolog.report.application.dto.ability.AbilityCreateRequest;
import wooteco.prolog.report.application.dto.ability.AbilityResponse;
import wooteco.prolog.report.application.dto.ability.AbilityUpdateRequest;
import wooteco.prolog.report.exception.AbilityNotFoundException;

public class AbilityStepDefinitions extends AcceptanceSteps {

    @When("부모역량 {string}(을)(를) 추가하(면)(고)")
    public void 부모역량을추가하면(String abilityName) {
        AbilityAcceptanceFixture fixture = AbilityAcceptanceFixture.findByName(abilityName);

        AbilityCreateRequest request = fixture.toCreateRequestWithParentId(null);
        context.invokeHttpPostWithToken("/abilities", request);
    }

    @When("{string}의 자식역량 {string}(을)(를) 추가하(면)(고)")
    public void 자식역량을추가하면(String parentAbility, String childAbility) {
        AbilityAcceptanceFixture fixture = AbilityAcceptanceFixture.findByName(childAbility);
        Long parentAbilityId = getAbilityIdByName(parentAbility);

        AbilityCreateRequest request = fixture.toCreateRequestWithParentId(parentAbilityId);
        context.invokeHttpPostWithToken("/abilities", request);
    }

    @When("역량 목록을 조회하면")
    public void 역량목록을조회하면() {
        context.invokeHttpGetWithToken("/abilities");
    }

    @Then("역량 목록을 받는다.")
    public void 역량목록을받는다() {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());

        List<AbilityResponse> responses = context.response.jsonPath()
            .getList(".", AbilityResponse.class);
        assertThat(responses).isNotEmpty();
    }

    @Then("역량이 추가된다.")
    public void 역량이추가된다() {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private Long getAbilityIdByName(String abilityName) {
        context.invokeHttpGetWithToken("/abilities");
        List<AbilityResponse> responses = context.response.jsonPath().getList(".", AbilityResponse.class);

        return responses.stream().filter(response -> abilityName.equals(response.getName()))
            .map(AbilityResponse::getId)
            .findAny()
            .orElseThrow(AbilityNotFoundException::new);
    }

    @When("부모 역량 목록을 조회하면")
    public void 부모역량목록을조회하면() {
        context.invokeHttpGetWithToken("/abilities/parent-only");
    }

    @Then("부모 역량 목록을 받는다.")
    public void 부모역량목록을받는다() {
        List<AbilityResponse> responses = context.response.jsonPath().getList(".", AbilityResponse.class);
        assertThat(responses.stream().allMatch(AbilityResponse::getIsParent)).isTrue();
    }

    @When("{string} 역량을 이름 {string}, 설명 {string}, 색상 {string} 으로 수정하면")
    public void 역량을이름설명색상으로수정하고(String abilityName, String name, String description, String color) {
        Long abilityId = getAbilityIdByName(abilityName);

        AbilityUpdateRequest request = new AbilityUpdateRequest(abilityId, name, description, color);
        context.invokeHttpPutWithToken("/abilities/" + abilityId, request);
    }

    @Then("이름 {string}, 설명 {string}, 색상 {string} 역량이 포함된 역량 목록을 받는다.")
    public void 이름설명색상역량이포함된역량목록을받는다(String name, String description, String color) {
        List<AbilityResponse> responses = context.response.jsonPath()
            .getList(".", AbilityResponse.class);

        assertThat(responses.stream().anyMatch(response ->
            name.equals(response.getName()) &&
            description.equals(response.getDescription()) &&
            color.equals(response.getColor()))
        ).isTrue();
    }

    @And("{string} 역량을 삭제하고")
    @When("{string} 역량을 삭제하면")
    public void 역량을삭제하고(String abilityName) {
        Long abilityId = getAbilityIdByName(abilityName);

        context.invokeHttpDeleteWithToken("/abilities/" + abilityId);
    }

    @Then("{string} 역량이 포함되지 않은 목록을 받는다.")
    public void 역량이포함되지않은목록을받는다(String abilityName) {
        List<AbilityResponse> responses = context.response.jsonPath()
            .getList(".", AbilityResponse.class);

        assertThatThrownBy(() -> getAbilityIdByName(abilityName))
            .isExactlyInstanceOf(AbilityNotFoundException.class);
        assertThat(responses.stream()
            .anyMatch(response -> abilityName.equals(response.getName()))
        ).isFalse();
    }

    @Then("삭제가 불가능하다는 예외가 발생한다.")
    public void 삭제가불가능하다는예외가발생한다() {
        ExceptionDto exceptionDto = context.response.as(ExceptionDto.class);

        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(exceptionDto.getCode()).isEqualTo(BadRequestCode.ABILITY_HAS_CHILDREN.getCode());
        assertThat(exceptionDto.getMessage()).isEqualTo(BadRequestCode.ABILITY_HAS_CHILDREN.getMessage());
    }

    @Then("역량 이름 중복 관련 예외가 발생한다.")
    public void 역량이름중복관련예외가발생한다() {
        ExceptionDto exceptionDto = context.response.as(ExceptionDto.class);

        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(exceptionDto.getCode()).isEqualTo(BadRequestCode.ABILITY_NAME_DUPLICATE.getCode());
        assertThat(exceptionDto.getMessage()).isEqualTo(BadRequestCode.ABILITY_NAME_DUPLICATE.getMessage());
    }

    @Then("부모역량 색상 중복 관련 예외가 발생한다.")
    public void 부모역량색상중복관련예외가발생한다() {
        ExceptionDto exceptionDto = context.response.as(ExceptionDto.class);

        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(exceptionDto.getCode()).isEqualTo(BadRequestCode.ABILITY_PARENT_COLOR_DUPLICATE.getCode());
        assertThat(exceptionDto.getMessage()).isEqualTo(BadRequestCode.ABILITY_PARENT_COLOR_DUPLICATE.getMessage());
    }

    @Then("부모역량과 자식역량의 색상 불일치 예외가 발생한다.")
    public void 부모역량과자식역량의색상불일치예외가발생한다() {
        ExceptionDto exceptionDto = context.response.as(ExceptionDto.class);

        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(exceptionDto.getCode()).isEqualTo(BadRequestCode.ABILITY_PARENT_CHILD_COLOR_DIFFERENT.getCode());
        assertThat(exceptionDto.getMessage()).isEqualTo(BadRequestCode.ABILITY_PARENT_CHILD_COLOR_DIFFERENT.getMessage());
    }
}
