package wooteco.prolog.docu;

import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import io.restassured.RestAssured;
import java.util.ArrayList;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.prolog.NewDocumentation;
import wooteco.prolog.session.application.LevelService;
import wooteco.prolog.session.application.dto.LevelRequest;
import wooteco.prolog.session.application.dto.LevelResponse;
import wooteco.prolog.session.ui.LevelController;

@WebMvcTest(controllers = LevelController.class)
public class LevelDocumentation extends NewDocumentation {

    @MockBean
    private LevelService levelService;

    @Test
    void 레벨_추가() {
        when(levelService.create(any())).thenReturn(LEVEL_RESPONSE);

        given.header(AUTHORIZATION, "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .body(LEVEL_REQUEST)
            .when().post("/levels")
            .then().log().all().apply(document("levels/create")).statusCode(HttpStatus.OK.value());

    }

    @Test
    void 레벨_목록_조회() {
        when(levelService.findAll()).thenReturn(LEVEL_RESPONSES);

        given
            .when().get("/levels")
            .then().log().all().apply(document("levels/list")).statusCode(HttpStatus.OK.value());
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

    private static final LevelRequest LEVEL_REQUEST = new LevelRequest("새로운 레벨");

    private static final LevelResponse LEVEL_RESPONSE = new LevelResponse(1L, "새로운 레벨1");

    public static final ArrayList<LevelResponse> LEVEL_RESPONSES = Lists
        .newArrayList(
            new LevelResponse(1L, "새로운 레벨1"),
            new LevelResponse(2L, "새로운 레벨2"),
            new LevelResponse(3L, "새로운 레벨3")
        );
}
