package wooteco.prolog.docu;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;
import javax.persistence.DiscriminatorValue;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.session.application.dto.SessionRequest;

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
}
