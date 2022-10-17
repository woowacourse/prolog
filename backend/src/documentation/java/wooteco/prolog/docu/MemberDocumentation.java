package wooteco.prolog.docu;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static wooteco.prolog.ResponseFixture.MEMBER;

import io.restassured.module.mockmvc.response.ValidatableMockMvcResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import wooteco.prolog.GithubResponses;
import wooteco.prolog.NewDocumentation;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.dto.MemberUpdateRequest;
import wooteco.prolog.member.ui.MemberController;

@WebMvcTest(controllers = MemberController.class)
public class MemberDocumentation extends NewDocumentation {

    @MockBean
    private MemberService memberService;

    @Deprecated
    @Test
    void 사용자_본인_정보를_조회한다() {
        //given
        given(memberService.findById(any()))
                .willReturn(MEMBER);

        //when
        ValidatableMockMvcResponse response = given
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/me")
                .then().log().all();

        //then
        response.expect(status().isOk());

        //docs
        response.apply(document("members/me"));
    }

    @Test
    void 자신의_사용자_정보를_수정한다() {
        //given, when
        MemberUpdateRequest params = new MemberUpdateRequest(
                "다른이름",
                "https://avatars.githubusercontent.com/u/51393021?v=4");
        ValidatableMockMvcResponse response = given
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().put("/members/{username}", GithubResponses.소롱.getLogin())
                .then().log().all();

        //then
        response.expect(status().isOk());

        //docs
        response.apply(document("members/edit"));
    }

    @Test
    void 사용자_정보를_조회한다() {
        //given
        given(memberService.findByUsername(any()))
                .willReturn(MEMBER);

        //when
        ValidatableMockMvcResponse response = given
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/{username}", GithubResponses.소롱.getLogin())
                .then().log().all();

        //then
        response.expect(status().isOk());

        //docs
        response.apply(document("members/read"));
    }
}
