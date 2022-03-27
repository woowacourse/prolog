package wooteco.prolog.docu;

import static org.apache.http.HttpHeaders.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;
import static wooteco.prolog.GithubResponses.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.GithubResponses;
import wooteco.prolog.login.application.dto.TokenRequest;
import wooteco.prolog.login.application.dto.TokenResponse;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.ability.application.dto.AbilityCreateRequest;
import wooteco.prolog.ability.application.dto.AbilityResponse;
import wooteco.prolog.ability.application.dto.AbilityUpdateRequest;
import wooteco.prolog.ability.application.dto.ChildAbilityResponse;
import wooteco.prolog.ability.application.dto.DefaultAbilityCreateRequest;
import wooteco.prolog.report.exception.AbilityNotFoundException;

public class AbilityDocumentation extends Documentation {

    private String accessToken;

    @BeforeEach
    void setUp() {
        accessToken = 로그인_사용자.getAccessToken();
    }

    @Test
    void 상위_역량을_생성한다() {
        // given
        AbilityCreateRequest request = new AbilityCreateRequest("상위 역량 이름", "상위 역량 설명", "#001122", null);

        // when
        ExtractableResponse<Response> response = given("abilities/create-parent")
            .header(AUTHORIZATION, "Bearer " + accessToken)
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/abilities")
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }

    @Test
    void 하위_역량을_생성한다() {
        // given
        AbilityCreateRequest 상위_역량_생성_request = new AbilityCreateRequest("상위 역량 이름", "상위 역량 설명", "#001122", null);
        AbilityResponse 상위_역량_response = 상위_역량을_생성하고_response를_반환한다(accessToken, 상위_역량_생성_request);

        AbilityCreateRequest request = new AbilityCreateRequest("하위 역량 이름", "하위 역량 설명", "#001122", 상위_역량_response.getId());

        // when
        ExtractableResponse<Response> response = given("abilities/create-child")
            .header(AUTHORIZATION, "Bearer " + accessToken)
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/abilities")
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }

    @Test
    void 역량_생성시_이름이_중복되면_예외가_발생한다() {
        // given
        String abilityName = "역량 이름";

        AbilityCreateRequest 앞선_역량_생성_request = new AbilityCreateRequest(abilityName, "앞선 역량 설명", "#001122", null);
        역량을_생성한다(accessToken, 앞선_역량_생성_request);

        AbilityCreateRequest request = new AbilityCreateRequest(abilityName, "역량 설명", "#ffffff", null);

        // when
        ExtractableResponse<Response> response = given("abilities/create-name-duplicate-exception")
            .header(AUTHORIZATION, "Bearer " + accessToken)
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/abilities")
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.value());
        assertThat((int) response.jsonPath().get("code")).isEqualTo(4002);
        assertThat((String) response.jsonPath().get("message")).isEqualTo("중복된 이름의 역량이 존재합니다.");
    }

    @Test
    void 역량_생성시_상위하위_관계가_아닌_역량끼리_색상이_중복되면_예외가_발생한다() {
        // given
        String abilityColor = "#ffffff";

        AbilityCreateRequest 앞선_역량_생성_request = new AbilityCreateRequest("앞선 역량 이름", "앞선 역량 설명", abilityColor, null);
        역량을_생성한다(accessToken, 앞선_역량_생성_request);

        AbilityCreateRequest request = new AbilityCreateRequest("역량 이름", "역량 설명", abilityColor, null);

        // when
        ExtractableResponse<Response> response = given("abilities/create-color-duplicate-exception")
            .header(AUTHORIZATION, "Bearer " + accessToken)
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/abilities")
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.value());
        assertThat((int) response.jsonPath().get("code")).isEqualTo(4003);
        assertThat((String) response.jsonPath().get("message")).isEqualTo("중복된 색상의 부모역량이 존재합니다.");
    }

    @Test
    void 하위_역량_생성시_상위_관계의_역량과_색상이_다르면_에외가_발생한다() {
        // given
        AbilityCreateRequest 상위_역량_생성_request = new AbilityCreateRequest("상위 역량 이름", "상위 역량 설명", "#001122", null);
        AbilityResponse 상위_역량_response = 상위_역량을_생성하고_response를_반환한다(accessToken, 상위_역량_생성_request);

        AbilityCreateRequest request = new AbilityCreateRequest("하위 역량 이름", "하위 역량 설명", "#ffffff", 상위_역량_response.getId());

        // when
        ExtractableResponse<Response> response = given("abilities/create-color-different-exception")
            .header(AUTHORIZATION, "Bearer " + accessToken)
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/abilities")
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.value());
        assertThat((int) response.jsonPath().get("code")).isEqualTo(4004);
        assertThat((String) response.jsonPath().get("message")).isEqualTo("상위 역량과 하위 역량의 색상이 일치하지 않습니다.");
    }

    @Test
    void 역량_목록을_조회한다() {
        // given
        AbilityResponse 상위_역량_response1 = 상위_역량을_생성하고_response를_반환한다(accessToken, new AbilityCreateRequest("상위 역량 이름1", "상위 역량 설명1", "#001122", null));
        AbilityResponse 상위_역량_response2 = 상위_역량을_생성하고_response를_반환한다(accessToken, new AbilityCreateRequest("상위 역량 이름2", "상위 역량 설명2", "#ffffff", null));
        역량을_생성한다(accessToken, new AbilityCreateRequest("하위 역량 이름1", "하위 역량 설명1", 상위_역량_response1.getColor(), 상위_역량_response1.getId()));
        역량을_생성한다(accessToken, new AbilityCreateRequest("하위 역량 이름2", "하위 역량 설명2", 상위_역량_response2.getColor(), 상위_역량_response2.getId()));

        // when
        ExtractableResponse<Response> response = given("abilities/read")
            .header(AUTHORIZATION, "Bearer " + accessToken)
            .when()
            .get("/abilities")
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
        List<AbilityResponse> abilityResponses = response.jsonPath().getList(".", AbilityResponse.class);
        assertThat(abilityResponses).hasSize(4);
    }

    @Test
    void 상위_역량_목록을_조회한다() {
        // given
        AbilityResponse 상위_역량_response1 = 상위_역량을_생성하고_response를_반환한다(accessToken, new AbilityCreateRequest("상위 역량 이름1", "상위 역량 설명1", "#001122", null));
        AbilityResponse 상위_역량_response2 = 상위_역량을_생성하고_response를_반환한다(accessToken, new AbilityCreateRequest("상위 역량 이름2", "상위 역량 설명2", "#ffffff", null));
        역량을_생성한다(accessToken, new AbilityCreateRequest("하위 역량 이름1", "하위 역량 설명1", 상위_역량_response1.getColor(), 상위_역량_response1.getId()));
        역량을_생성한다(accessToken, new AbilityCreateRequest("하위 역량 이름2", "하위 역량 설명2", 상위_역량_response2.getColor(), 상위_역량_response2.getId()));

        // when
        ExtractableResponse<Response> response = given("abilities/read/parent-only")
            .header(AUTHORIZATION, "Bearer " + accessToken)
            .when()
            .get("/abilities/parent-only")
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
        List<AbilityResponse> abilityResponses = response.jsonPath().getList(".", AbilityResponse.class);
        assertThat(abilityResponses).hasSize(2);
    }

    @Test
    void 멤버_Username을_통해_역량_목록을_조회한다() {
        // given
        String 서니_accessToken = 로그인한다(서니);
        String 서니_username = 멤버username을_추출한다(서니);

        AbilityResponse 상위_역량_response1 = 상위_역량을_생성하고_response를_반환한다(서니_accessToken, new AbilityCreateRequest("유저의 상위 역량 이름1", "유저의 상위 역량 설명1", "#001122", null));
        AbilityResponse 상위_역량_response2 = 상위_역량을_생성하고_response를_반환한다(서니_accessToken, new AbilityCreateRequest("유저의 상위 역량 이름2", "유저의 상위 역량 설명2", "#ffffff", null));
        역량을_생성한다(서니_accessToken, new AbilityCreateRequest("유저의 하위 역량 이름1", "유저의 하위 역량 설명1", 상위_역량_response1.getColor(), 상위_역량_response1.getId()));
        역량을_생성한다(서니_accessToken, new AbilityCreateRequest("유저의 하위 역량 이름2", "유저의 하위 역량 설명2", 상위_역량_response2.getColor(), 상위_역량_response2.getId()));

        // when
        ExtractableResponse<Response> response = given("abilities/read/username")
            .header(AUTHORIZATION, "Bearer " + accessToken)
            .when()
            .get(String.format("/members/%s/abilities", 서니_username))
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
        List<AbilityResponse> abilityResponses = response.jsonPath().getList(".", AbilityResponse.class);
        assertThat(abilityResponses).hasSize(4);
    }

    @Test
    void 역량을_수정한다() {
        // given
        AbilityCreateRequest 역량_생성_request = new AbilityCreateRequest("역량 이름", "역량 설명", "#000000", null);
        Long 생성된_역량_ID = 상위_역량을_생성하고_response를_반환한다(accessToken, 역량_생성_request).getId();

        String 새로운_이름 = "새로운 이름";
        String 새로운_설명 = "새로운 설명";
        String 새로운_색상 = "#ffffff";
        AbilityUpdateRequest request = new AbilityUpdateRequest(생성된_역량_ID, 새로운_이름, 새로운_설명, 새로운_색상);

        // when
        ExtractableResponse<Response> response = given("abilities/update")
            .header(AUTHORIZATION, "Bearer " + accessToken)
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .put("/abilities/" + 생성된_역량_ID)
            .then()
            .log().all()
            .extract();

        // then
        AbilityResponse updatedAbilityResponse = response.jsonPath()
            .getList(".", AbilityResponse.class)
            .stream()
            .filter(ability -> ability.getId().equals(생성된_역량_ID))
            .findAny()
            .orElseThrow(AbilityNotFoundException::new);

        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(updatedAbilityResponse.getId()).isEqualTo(생성된_역량_ID);
        assertThat(updatedAbilityResponse.getName()).isEqualTo(새로운_이름);
        assertThat(updatedAbilityResponse.getDescription()).isEqualTo(새로운_설명);
        assertThat(updatedAbilityResponse.getColor()).isEqualTo(새로운_색상);
    }

    @Test
    void 상위_역량_수정시_하위_역량의_색이_모두_변경된다() {
    // given
        AbilityResponse 생성된_상위_역량 = 상위_역량을_생성하고_response를_반환한다(accessToken, new AbilityCreateRequest("역량 이름", "역량 설명", "#000000", null));
        역량을_생성한다(accessToken, new AbilityCreateRequest("하위 역량 이름", "하위 역량 설명", 생성된_상위_역량.getColor(), 생성된_상위_역량.getId()));

        String 새로운_이름 = "새로운 이름";
        String 새로운_설명 = "새로운 설명";
        String 새로운_색상 = "#ffffff";
        AbilityUpdateRequest request = new AbilityUpdateRequest(생성된_상위_역량.getId(), 새로운_이름, 새로운_설명, 새로운_색상);

        // when
        ExtractableResponse<Response> response = given("abilities/update-with-children-color")
            .header(AUTHORIZATION, "Bearer " + accessToken)
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .put("/abilities/" + 생성된_상위_역량.getId())
            .then()
            .log().all()
            .extract();

        // then
        AbilityResponse updatedAbilityResponse = response.jsonPath()
            .getList(".", AbilityResponse.class)
            .stream()
            .filter(ability -> ability.getId().equals(생성된_상위_역량.getId()))
            .findAny()
            .orElseThrow(AbilityNotFoundException::new);

        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(updatedAbilityResponse.getId()).isEqualTo(생성된_상위_역량.getId());
        assertThat(updatedAbilityResponse.getName()).isEqualTo(새로운_이름);
        assertThat(updatedAbilityResponse.getDescription()).isEqualTo(새로운_설명);
        assertThat(updatedAbilityResponse.getColor()).isEqualTo(새로운_색상);
    }

    @Test
    void 하위_역량_수정시_색상_변경이_무시된다() {
        // given
        AbilityResponse 생성된_상위_역량 = 상위_역량을_생성하고_response를_반환한다(accessToken, new AbilityCreateRequest("역량 이름", "역량 설명", "#000000", null));
        역량을_생성한다(accessToken, new AbilityCreateRequest("하위 역량 이름", "하위 역량 설명", 생성된_상위_역량.getColor(), 생성된_상위_역량.getId()));

        Long 생성된_하위_역량_ID = 역량_목록을_조회한다(accessToken).jsonPath().getList(".", AbilityResponse.class)
            .stream()
            .filter(response -> response.getName().equals("하위 역량 이름"))
            .findAny()
            .orElseThrow(AbilityNotFoundException::new)
            .getId();

        String 새로운_이름 = "새로운 이름";
        String 새로운_설명 = "새로운 설명";
        String 새로운_색상 = "#ffffff";
        AbilityUpdateRequest request = new AbilityUpdateRequest(생성된_하위_역량_ID, 새로운_이름, 새로운_설명, 새로운_색상);

        // when
        ExtractableResponse<Response> response = given("abilities/update-ignore-child-color")
            .header(AUTHORIZATION, "Bearer " + accessToken)
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .put("/abilities/" + 생성된_하위_역량_ID)
            .then()
            .log().all()
            .extract();

        // then
        AbilityResponse updatedAbilityResponse = response.jsonPath()
            .getList(".", AbilityResponse.class)
            .stream()
            .filter(ability -> ability.getId().equals(생성된_하위_역량_ID))
            .findAny()
            .orElseThrow(AbilityNotFoundException::new);

        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(updatedAbilityResponse.getId()).isEqualTo(생성된_하위_역량_ID);
        assertThat(updatedAbilityResponse.getName()).isEqualTo(새로운_이름);
        assertThat(updatedAbilityResponse.getDescription()).isEqualTo(새로운_설명);
        assertThat(updatedAbilityResponse.getColor()).isEqualTo(생성된_상위_역량.getColor());
        assertThat(updatedAbilityResponse.getColor()).isNotEqualTo(새로운_색상);
    }

    @Test
    void 역량_수정시_이름이_중복되면_예외가_발생한다() {
        // given
        AbilityCreateRequest 역량_생성_request = new AbilityCreateRequest("역량 이름", "역량 설명", "#000000", null);
        AbilityCreateRequest 또_다른_역량_생성_request = new AbilityCreateRequest("또 다른 역량 이름", "또 다른 역량 설명", "#111111", null);
        Long 생성된_역량_ID = 상위_역량을_생성하고_response를_반환한다(accessToken, 역량_생성_request).getId();
        AbilityResponse 생성된_또_다른_역량 = 상위_역량을_생성하고_response를_반환한다(accessToken, 또_다른_역량_생성_request);

        AbilityUpdateRequest request = new AbilityUpdateRequest(생성된_역량_ID, 생성된_또_다른_역량.getName(), "완전히 새로워!", "#999999");

        // when
        ExtractableResponse<Response> response = given("abilities/update-duplicate-name-exception")
            .header(AUTHORIZATION, "Bearer " + accessToken)
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .put("/abilities/" + 생성된_역량_ID)
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.value());
        assertThat((int) response.jsonPath().get("code")).isEqualTo(4002);
        assertThat((String) response.jsonPath().get("message")).isEqualTo("중복된 이름의 역량이 존재합니다.");
    }

    @Test
    void 역량_수정시_색상이_중복되면_예외가_발생한다() {
        // given
        AbilityCreateRequest 역량_생성_request = new AbilityCreateRequest("역량 이름", "역량 설명", "#000000", null);
        AbilityCreateRequest 또_다른_역량_생성_request = new AbilityCreateRequest("또 다른 역량 이름", "또 다른 역량 설명", "#111111", null);
        Long 생성된_역량_ID = 상위_역량을_생성하고_response를_반환한다(accessToken, 역량_생성_request).getId();
        AbilityResponse 생성된_또_다른_역량 = 상위_역량을_생성하고_response를_반환한다(accessToken, 또_다른_역량_생성_request);

        AbilityUpdateRequest request = new AbilityUpdateRequest(생성된_역량_ID, "완전히 색다른 이름!", "완전히 새로워!", 생성된_또_다른_역량.getColor());

        // when
        ExtractableResponse<Response> response = given("abilities/update-duplicate-color-exception")
            .header(AUTHORIZATION, "Bearer " + accessToken)
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .put("/abilities/" + 생성된_역량_ID)
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.value());
        assertThat((int) response.jsonPath().get("code")).isEqualTo(4003);
        assertThat((String) response.jsonPath().get("message")).isEqualTo("중복된 색상의 부모역량이 존재합니다.");
    }

    @Test
    void 역량을_제거한다() {
        // given
        AbilityCreateRequest 역량_생성_request = new AbilityCreateRequest("역량 이름", "역량 설명", "#000000", null);
        Long 생성된_역량_ID = 상위_역량을_생성하고_response를_반환한다(accessToken, 역량_생성_request).getId();

        // when
        ExtractableResponse<Response> response = given("abilities/delete")
            .header(AUTHORIZATION, "Bearer " + accessToken)
            .when()
            .delete("/abilities/" + 생성된_역량_ID)
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }

    @Test
    void 백엔드_기본_역량을_등록한다() {
        // given
        기본_역량을_생성한다(new DefaultAbilityCreateRequest("Java", "Java 입니다.", "#111111", "be"));

        // when
        ExtractableResponse<Response> response = given("abilities/create-template-be")
            .header(AUTHORIZATION, "Bearer " + accessToken)
            .when()
            .post("/abilities/templates/be")
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }

    @Test
    void 프론트엔드_기본_역량을_등록한다() {
        // given
        기본_역량을_생성한다(new DefaultAbilityCreateRequest("JavaScript", "JavaScript 입니다.", "#222222", "fe"));

        // when
        ExtractableResponse<Response> response = given("abilities/create-template-fe")
            .header(AUTHORIZATION, "Bearer " + accessToken)
            .when()
            .post("/abilities/templates/fe")
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }

    @Test
    void 기본_역량_등록시_잘못된_과정을_선택하면_예외가_발생한다() {
        // when
        ExtractableResponse<Response> response = given("abilities/create-template-exception")
            .header(AUTHORIZATION, "Bearer " + accessToken)
            .when()
            .post("/abilities/template/wrong-path")
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.value());
        assertThat((int) response.jsonPath().get("code")).isEqualTo(4006);
        assertThat((String) response.jsonPath().get("message")).isEqualTo("입력된 과정의 기본 역량이 존재하지 않습니다.");
    }

    private String 로그인한다(GithubResponses githubResponse) {
        return RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new TokenRequest(githubResponse.getCode()))
            .when()
            .post("/login/token")
            .then()
            .log().all()
            .extract().body().as(TokenResponse.class).getAccessToken();
    }

    private String 멤버username을_추출한다(GithubResponses githubResponse) {
        return RestAssured.given()
            .when()
            .get("/members/" + githubResponse.getLogin())
            .then()
            .log().all()
            .extract().body().as(MemberResponse.class).getUsername();
    }

    private AbilityResponse 상위_역량을_생성하고_response를_반환한다(String accessToken, AbilityCreateRequest request) {
        역량을_생성한다(accessToken, request);

        ExtractableResponse<Response> 역량_목록_조회 = 역량_목록을_조회한다(accessToken);
        List<AbilityResponse> abilityResponses = 역량_목록_조회.jsonPath().getList(".", AbilityResponse.class);

        return abilityResponses.stream()
            .filter(ability -> ability.getName().equals(request.getName()))
            .findAny()
            .orElseThrow(AbilityNotFoundException::new);
    }

    private ExtractableResponse<Response> 역량을_생성한다(String accessToken, AbilityCreateRequest request) {
        return RestAssured.given()
            .header(AUTHORIZATION, "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when()
            .post("/abilities")
            .then()
            .log().all()
            .extract();
    }

    private ExtractableResponse<Response> 역량_목록을_조회한다(String accessToken) {
        return RestAssured.given()
            .header(AUTHORIZATION, "Bearer" + accessToken)
            .when()
            .get("/abilities")
            .then()
            .log().all()
            .extract();
    }

    private void 기본_역량을_생성한다(DefaultAbilityCreateRequest request) {
        RestAssured.given()
            .header(AUTHORIZATION, "Bearer " + accessToken)
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/abilities/default")
            .then()
            .log().all()
            .extract();
    }
}
