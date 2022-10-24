package wooteco.prolog.docu;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static wooteco.prolog.ResponseFixture.CALENDER_STUDYLOG_RESPONSES;
import static wooteco.prolog.ResponseFixture.MEMBER_TAB_RESPONSES;

import io.restassured.module.mockmvc.response.ValidatableMockMvcResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import wooteco.prolog.GithubResponses;
import wooteco.prolog.NewDocumentation;
import wooteco.prolog.member.application.MemberTagService;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.ui.StudylogOverviewController;

@WebMvcTest(controllers = StudylogOverviewController.class)
public class StudylogOverviewDocumentation extends NewDocumentation {

    @MockBean
    private MemberTagService memberTagService;

    @MockBean
    private StudylogService studylogService;

    @Test
    void 유저의_태그를_조회한다() {
        //given
        given(memberTagService.findByMember(any()))
                .willReturn(MEMBER_TAB_RESPONSES);

        //when
        ValidatableMockMvcResponse response = given
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/{username}/tags", GithubResponses.브라운.getLogin())
                .then().log().all();

        //then
        response.expect(status().isOk());

        //docs
        response.apply(document("members/tags"));
    }

    @Test
    void 유저의_달력_스터디로그를_조회한다() {
        //given
        given(studylogService.findCalendarStudylogs(any(), any()))
                .willReturn(CALENDER_STUDYLOG_RESPONSES);

        //when
        ValidatableMockMvcResponse response = given
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("year", 2021)
                .param("month", 8)
                .when().get("/members/{username}/calendar-studylogs", GithubResponses.브라운.getLogin())
                .then().log().all();

        //then
        response.expect(status().isOk());

        //docs
        response.apply(document("members/calendar-studylogs"));
    }
}
