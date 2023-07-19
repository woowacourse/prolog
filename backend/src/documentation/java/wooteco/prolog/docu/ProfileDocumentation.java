package wooteco.prolog.docu;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static wooteco.prolog.ResponseFixture.MEMBER_RESPONSE;
import static wooteco.prolog.ResponseFixture.STUDYLOGS_RESPONSE;

import io.restassured.module.mockmvc.response.ValidatableMockMvcResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import wooteco.prolog.GithubResponses;
import wooteco.prolog.NewDocumentation;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.dto.ProfileIntroRequest;
import wooteco.prolog.member.application.dto.ProfileIntroResponse;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.ui.ProfileStudylogController;

@WebMvcTest(controllers = ProfileStudylogController.class)
public class ProfileDocumentation extends NewDocumentation {

    @MockBean
    private StudylogService studylogService;

    @MockBean
    private MemberService memberService;

    @Test
    public void 스터디로그_목록을_작성자별로_조회한다() {
        //given
        given(studylogService
            .findStudylogsWithoutKeyword(any(), any(), any(), any(), any(), any(), any(), any(),
                any()))
            .willReturn(STUDYLOGS_RESPONSE);

        //when
        ValidatableMockMvcResponse response = given
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/members/{username}/studylogs", GithubResponses.소롱.getLogin())
            .then().log().all();

        //then
        response.expect(status().isOk());

        //docs
        response.apply(document("profile/studylog"));
    }

    @Test
    void 사용자_프로필을_조회한다() {
        //given
        given(memberService.findMemberResponseByUsername(any()))
            .willReturn(MEMBER_RESPONSE);

        //when
        ValidatableMockMvcResponse response = given
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/members/{username}/profile", GithubResponses.소롱.getLogin())
            .then().log().all();

        //then
        response.expect(status().isOk());

        //docs
        response.apply(document("profile/profile"));
    }

    @Test
    void 사용자_소개글을_조회한다() {
        //given
        given(memberService.findProfileIntro(any()))
            .willReturn(new ProfileIntroResponse("안녕하세요 잉입니다."));

        //when
        ValidatableMockMvcResponse response = given
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/members/{username}/profile-intro", GithubResponses.소롱.getLogin())
            .then().log().all();

        //then
        response.expect(status().isOk());

        //docs
        response.apply(document("profile/profile-intro"));
    }

    @Test
    void 사용자가_본인의_소개글을_수정한다() {
        //given
        given(memberService.findProfileIntro(any()))
            .willReturn(new ProfileIntroResponse("안녕하세요 잉입니다."));

        //when
        ProfileIntroRequest params = new ProfileIntroRequest("수정된 소개글 입니다.");
        ValidatableMockMvcResponse response = given
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .body(params)
            .when().put("/members/{username}/profile-intro", GithubResponses.소롱.getLogin())
            .then().log().all();

        //then
        response.expect(status().isOk());

        //docs
        response.apply(document("profile/update-profile-intro"));
    }
}
