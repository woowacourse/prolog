package wooteco.prolog.docu;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.session.application.dto.SessionRequest;
import wooteco.prolog.session.application.dto.SessionResponse;

public class SessionMemberDocumentation extends Documentation {

    @Test
    void 강의에_자신을_등록한다() {
        //given
        final SessionRequest sessionRequest = new SessionRequest("새강의");
        ExtractableResponse<Response> createResponse = given("session")
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(sessionRequest)
                .when().post("/sessions")
                .then().log().all().extract();
        Map<String, Object> values = createResponse.jsonPath().get();
        //when
        ExtractableResponse<Response> createResponse2 = given("session")
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/sessions/" + values.get("id") + "/members/me")
                .then().log().all().extract();
        //then
        assertThat(createResponse2.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 강의에서_자신을_제거한다() {
        // given
        SessionResponse sessionResponse = createSessionWithToken();
        createSessionMemberWithToken(sessionResponse.getId());

        // when
        ExtractableResponse<Response> response = given("session")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().delete("/sessions/" + sessionResponse.getId() + "/members/me")
            .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private SessionResponse createSessionWithToken() {
        SessionRequest sessionRequest = new SessionRequest("새강의");

        ExtractableResponse<Response> createResponse = given("session")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(sessionRequest)
            .when().post("/sessions")
            .then().log().all().extract();

        return createResponse.as(SessionResponse.class);
    }

    private void createSessionMemberWithToken(Long sessionId) {
        given("session")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/sessions/" + sessionId + "/members/me")
            .then().log().all().extract();
    }
}
