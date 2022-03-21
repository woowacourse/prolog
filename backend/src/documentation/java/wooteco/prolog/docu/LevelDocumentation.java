package wooteco.prolog.docu;

import io.restassured.RestAssured;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.session.application.dto.LevelRequest;

public class LevelDocumentation extends Documentation {

    @Test
    void 새로운_레벨을_추가한다() {
        LevelRequest levelRequest = new LevelRequest("새로운 레벨");

        given("levels/create")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .body(levelRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/levels")
            .then().log().all();
    }

    @Test
    void 레벨_목록을_조회한다() {
        List<LevelRequest> levels = Arrays
            .asList(new LevelRequest("새로운 레벨1"), new LevelRequest("새로운 레벨2"),
                new LevelRequest("새로운 레벨3"));

        for (LevelRequest level : levels) {
            레벨_등록함(level);
        }

        given("levels/list")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .when()
            .get("/levels")
            .then().log().all();
    }

    private void 레벨_등록함(LevelRequest request) {
        RestAssured
            .given().log().all()
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/levels")
            .then().log().all()
            .extract();
    }
}
