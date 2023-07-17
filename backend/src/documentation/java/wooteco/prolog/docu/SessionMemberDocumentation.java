package wooteco.prolog.docu;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.restassured.module.mockmvc.response.ValidatableMockMvcResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import wooteco.prolog.NewDocumentation;
import wooteco.prolog.session.application.SessionMemberService;
import wooteco.prolog.session.ui.SessionMemberController;

@WebMvcTest(controllers = SessionMemberController.class)
public class SessionMemberDocumentation extends NewDocumentation {

    @MockBean
    private SessionMemberService sessionMemberService;

    @Test
    void 강의에_자신을_등록한다() {
        //given, when
        ValidatableMockMvcResponse response = given
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/sessions/{sessionId}/members/me", 1L)
            .then().log().all();

        //then
        response.expect(status().isOk());

        //docs
        response.apply(document("sessions/register/me"));
    }

    @Test
    void 강의에서_자신을_제거한다() {
        //given, when
        ValidatableMockMvcResponse response = given
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().delete("/sessions/{sessionId}/members/me", 1L)
            .then().log().all();

        //then
        response.expect(status().isOk());

        //docs
        response.apply(document("sessions/delete/me"));
    }
}
