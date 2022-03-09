package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.ability.application.dto.AbilityCreateRequest;
import wooteco.prolog.ability.application.dto.AbilityResponse;
import wooteco.prolog.ability.application.dto.AbilityUpdateRequest;
import wooteco.prolog.ability.application.dto.DefaultAbilityCreateRequest;
import wooteco.prolog.common.exception.BadRequestCode;
import wooteco.prolog.common.exception.ExceptionDto;
import wooteco.prolog.fixtures.AbilityAcceptanceFixture;
import wooteco.prolog.fixtures.GithubResponses;
import wooteco.prolog.report.exception.AbilityNotFoundException;

public class AbilityStepDefinitions extends AcceptanceSteps {

    @When("부모역량 {string}(을)(를) 추가하(면)(고)")
    public void 부모역량을추가하면(String abilityName) {
        AbilityAcceptanceFixture fixture = AbilityAcceptanceFixture.findByName(abilityName);

        AbilityCreateRequest request = fixture.toCreateRequestWithParentId(null);
        context.invokeHttpPostWithToken("/abilities", request);
        context.storage.put(abilityName, context.response.as(AbilityResponse.class));
    }

    @When("{string}의 자식역량 {string}(을)(를) 추가하(면)(고)")
    public void 자식역량을추가하면(String parentAbility, String childAbility) {
        String username = (String) context.storage.get("username");
        AbilityAcceptanceFixture fixture = AbilityAcceptanceFixture.findByName(childAbility);
        Long parentAbilityId = getAbilityIdByName(username, parentAbility);

        AbilityCreateRequest request = fixture.toCreateRequestWithParentId(parentAbilityId);
        context.invokeHttpPostWithToken("/abilities", request);
        context.storage.put(childAbility, context.response.as(AbilityResponse.class));
    }

    @When("역량 목록을 조회하면")
    public void 역량목록을조회하면() {
        context.invokeHttpGetWithToken("/abilities");
    }

    @When("{string}의 역량 목록을 조회하면")
    public void 의역량목록을조회하면(String member) {
        String username = GithubResponses.findByName(member).getLogin();
        context.invokeHttpGetWithToken(String.format("/members/%s/abilities", username));
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

    @When("부모 역량 목록을 조회하면")
    public void 부모역량목록을조회하면() {
        context.invokeHttpGetWithToken("/abilities/parent-only");
    }

    @Then("부모 역량 목록을 받는다.")
    public void 부모역량목록을받는다() {
        List<AbilityResponse> responses = context.response.jsonPath().getList(".", AbilityResponse.class);
        assertThat(responses.stream().allMatch(AbilityResponse::isParent)).isTrue();
    }

    @And("{string} 역량을 이름 {string}, 설명 {string}, 색상 {string} 으로 수정하고")
    @When("{string} 역량을 이름 {string}, 설명 {string}, 색상 {string} 으로 수정하면")
    public void 역량을이름설명색상으로수정하고(String abilityName, String name, String description, String color) {
        Long abilityId = getAbilityIdByName(abilityName);

        AbilityUpdateRequest request = new AbilityUpdateRequest(abilityId, name, description, color);
        context.invokeHttpPutWithToken("/abilities/" + abilityId, request);
    }

    @Then("{string}의 자식역량 {string}도 {string}으로 바뀐다.")
    public void 의자식역량도으로바뀐다(String parentName, String childName, String color) {
        AbilityResponse parentResponse = parseResponseById(getAbilityIdByName(parentName));
        AbilityResponse childResponse = parseResponseById(getAbilityIdByName(childName));

        assertThat(parentResponse.getColor()).isEqualTo(color);
        assertThat(childResponse.getColor()).isEqualTo(color);
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
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
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

    @Then("자식역량 {string}의 색상은 {string}으로 바뀌지 않는다.")
    public void 자식역량의색상은으로바뀌지않는다(String childName, String color) {
        AbilityResponse response = parseResponseById(getAbilityIdByName(childName));

        assertThat(response.getColor()).isNotEqualTo(color);
    }

    @Then("비어있는 역량 목록을 받는다.")
    public void 비어있는역량목록을받는다() {
        List<AbilityResponse> responses = context.response.jsonPath()
            .getList(".", AbilityResponse.class);

        assertThat(responses).isEmpty();
    }

    @And("{string} 과정으로 기본 역량을 등록하고")
    @When("{string} 과정으로 기본 역량을 등록하면")
    public void 과정으로기본역량을등록하고(String template) {
        context.invokeHttpPostWithToken("/abilities/template/" + template);
    }

    @Then("기본 역량 조회 실패 관련 예외가 발생한다.")
    public void 기본역량조회실패관련예외가발생한다() {
        ExceptionDto exceptionDto = context.response.as(ExceptionDto.class);

        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(exceptionDto.getCode()).isEqualTo(BadRequestCode.DEFAULT_ABILITY_NOT_FOUND.getCode());
        assertThat(exceptionDto.getMessage()).isEqualTo(BadRequestCode.DEFAULT_ABILITY_NOT_FOUND.getMessage());
    }

    @And("관리자가 기본 역량 {string}을 {string} 과정으로 추가하고")
    public void 관리자가기본역량을과정으로추가하고(String defaultAbility, String template) {
        DefaultAbilityCreateRequest request = new DefaultAbilityCreateRequest(defaultAbility, "defaultAbility 입니다.", "#color", template);
        context.invokeHttpPostWithToken("/abilities/default", request);
    }

    private Long getAbilityIdByName(String abilityName) {
        context.invokeHttpGetWithToken("/members/{username}/abilities");
        List<AbilityResponse> responses = context.response.jsonPath().getList(".", AbilityResponse.class);

        return responses.stream().filter(response -> abilityName.equals(response.getName()))
            .map(AbilityResponse::getId)
            .findAny()
            .orElseThrow(AbilityNotFoundException::new);
    }

    private Long getAbilityIdByName(String username, String abilityName) {
        context.invokeHttpGetWithToken("/members/" + username + "/abilities");
        List<AbilityResponse> responses = context.response.jsonPath().getList(".", AbilityResponse.class);

        return responses.stream().filter(response -> abilityName.equals(response.getName()))
            .map(AbilityResponse::getId)
            .findAny()
            .orElseThrow(AbilityNotFoundException::new);
    }

    private AbilityResponse parseResponseById(Long id) {
        List<AbilityResponse> responses = context.response.jsonPath().getList(".", AbilityResponse.class);

        return responses.stream()
            .filter(response -> response.getId().equals(id))
            .findAny()
            .orElseThrow(AbilityNotFoundException::new);
    }
}
