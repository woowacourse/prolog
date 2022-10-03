package wooteco.prolog.docu;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static wooteco.prolog.ResponseFixture.MEMBER_RESPONSE;
import static wooteco.prolog.ResponseFixture.STUDY_LOGS_RESPONSE;
import static wooteco.prolog.ResponseFixture.STUDY_LOG_RESPONSE1;

import io.restassured.module.mockmvc.response.ValidatableMockMvcResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import wooteco.prolog.NewDocumentation;
import wooteco.prolog.member.application.dto.MemberScrapRequest;
import wooteco.prolog.member.application.dto.MemberScrapResponse;
import wooteco.prolog.member.ui.MemberReactionController;
import wooteco.prolog.studylog.application.StudylogScrapService;

@WebMvcTest(controllers = MemberReactionController.class)
public class MemberReactionDocumentation extends NewDocumentation {

    @MockBean
    private StudylogScrapService studylogScrapService;

    @Test
    void 스터디로그를_스크랩한다() {
        //given
        given(studylogScrapService.registerScrap(any(), any()))
                .willReturn(new MemberScrapResponse(MEMBER_RESPONSE, STUDY_LOG_RESPONSE1));

        //when
        MemberScrapRequest params = new MemberScrapRequest(1L);
        ValidatableMockMvcResponse response = given
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/members/scrap")
                .then().log().all();

        //then
        response.expect(status().isCreated());
        response.expect(header().exists("Location"));

        //docs
        response.apply(document("members/scrap/create"));
    }

    @Test
    void 스터디로그를_스크랩취소한다() {
        //given, when
        MemberScrapRequest params = new MemberScrapRequest(1L);
        ValidatableMockMvcResponse response = given
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .formParam("studylog", 1L)
                .when().delete("/members/scrap")
                .then().log().all();

        //then
        response.expect(status().isNoContent());

        //docs
        response.apply(document("members/scrap/delete"));
    }

    @Test
    void 스크랩한_스터디로그를_목록을_조회한다() {
        //given
        given(studylogScrapService.showScrap(any(), any()))
                .willReturn(STUDY_LOGS_RESPONSE);

        //when
        MemberScrapRequest params = new MemberScrapRequest(1L);
        ValidatableMockMvcResponse response = given
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().get("/members/scrap")
                .then().log().all();

        //then
        response.expect(status().isOk());

        //docs
        response.apply(document("members/scrap/list"));
    }
}
