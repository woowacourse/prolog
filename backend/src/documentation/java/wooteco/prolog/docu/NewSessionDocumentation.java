package wooteco.prolog.docu;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import wooteco.prolog.NewDocumentation;
import wooteco.prolog.roadmap.application.NewSessionService;
import wooteco.prolog.roadmap.application.dto.SessionRequest;
import wooteco.prolog.roadmap.application.dto.SessionResponse;
import wooteco.prolog.roadmap.application.dto.SessionsResponse;
import wooteco.prolog.roadmap.ui.NewSessionController;

@WebMvcTest(controllers = NewSessionController.class)
public class NewSessionDocumentation extends NewDocumentation {

    @MockBean
    private NewSessionService sessionService;

    @Test
    void 세션_생성() {
        given(sessionService.createSession(any(), any())).willReturn(1L);

        given
            .contentType("application/json;charset=UTF-8")
            .body(SESSION_CREATE_REQUEST)
            .when().post("/curriculums/1/sessions")
            .then().log().all().apply(document("sessions-new/create"))
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 세션_조회() {
        given(sessionService.findSessions(any())).willReturn(SESSIONS_RESPONSE);

        given
            .when().get("/curriculums/1/sessions")
            .then().log().all().apply(document("sessions-new/select"))
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 세션_수정() {
        doNothing().when(sessionService).updateSession(any(), any());

        given
            .contentType("application/json;charset=UTF-8")
            .body(SESSION_UPDATE_REQUEST)
            .when().put("/curriculums/1/sessions/1")
            .then().log().all().apply(document("sessions-new/update"))
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 세션_삭제() {
        doNothing().when(sessionService).deleteSession(any());

        given
            .when().delete("/curriculums/1/sessions/1")
            .then().log().all().apply(document("sessions-new/delete"))
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private static final SessionRequest SESSION_CREATE_REQUEST = new SessionRequest(
        "백엔드 레벨1"
    );

    private static final SessionsResponse SESSIONS_RESPONSE = new SessionsResponse(
        Arrays.asList(
            new SessionResponse(1L, "백엔드 레벨1"),
            new SessionResponse(2L, "백엔드 레벨2")
        )
    );

    private static final SessionRequest SESSION_UPDATE_REQUEST = new SessionRequest(
        "백엔드 레벨2"
    );
}
