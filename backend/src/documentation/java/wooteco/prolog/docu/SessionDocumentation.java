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
import wooteco.prolog.session.application.SessionService;
import wooteco.prolog.session.application.dto.SessionRequest;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.session.ui.SessionController;

@WebMvcTest(controllers = SessionController.class)
public class SessionDocumentation extends NewDocumentation {

    @MockBean
    private SessionService sessionService;

    @Test
    void 세션_추가() {
        when(sessionService.create(any())).thenReturn(LEVEL_RESPONSE);

        given.header(AUTHORIZATION, "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .body(LEVEL_REQUEST)
            .when().post("/sessions")
            .then().log().all().apply(document("sessions/create"))
            .statusCode(HttpStatus.CREATED.value());

    }

    @Test
    void 세션_목록_조회() {
        when(sessionService.findAll()).thenReturn(LEVEL_RESPONSES);

        given
            .when().get("/sessions")
            .then().log().all().apply(document("sessions/list")).statusCode(HttpStatus.OK.value());
    }

    private void 세션_등록함(SessionRequest request) {
        RestAssured
            .given().log().all()
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/sessions")
            .then().log().all()
            .extract();
    }

    private static final SessionRequest LEVEL_REQUEST = new SessionRequest("새로운 세션");

    private static final SessionResponse LEVEL_RESPONSE = new SessionResponse(1L, "새로운 세션1");

    public static final ArrayList<SessionResponse> LEVEL_RESPONSES = Lists
        .newArrayList(
            new SessionResponse(1L, "새로운 세션1"),
            new SessionResponse(2L, "새로운 세션2"),
            new SessionResponse(3L, "새로운 세션3")
        );
}
