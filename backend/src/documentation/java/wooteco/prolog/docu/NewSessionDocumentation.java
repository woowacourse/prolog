package wooteco.prolog.docu;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.prolog.NewDocumentation;
import wooteco.prolog.roadmap.application.NewSessionService;
import wooteco.prolog.roadmap.application.dto.SessionRequest;
import wooteco.prolog.roadmap.ui.NewSessionController;

@WebMvcTest(controllers = NewSessionController.class)
public class NewSessionDocumentation extends NewDocumentation {

    @MockBean
    private NewSessionService sessionService;

    @Test
    void 키워드_생성() {
        given(sessionService.createSession(any(), any())).willReturn(1L);

        given
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(SESSION_CREATE_REQUEST)
            .when().post("/curriculums/1/sessions")
            .then().log().all().apply(document("sessions-new/create"))
            .statusCode(HttpStatus.CREATED.value());
    }

    private static final SessionRequest SESSION_CREATE_REQUEST = new SessionRequest(
        "백엔드 레벨1"
    );
}
