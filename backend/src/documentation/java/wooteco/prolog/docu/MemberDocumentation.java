package wooteco.prolog.docu;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.GithubResponses;
import wooteco.prolog.member.application.dto.MemberUpdateRequest;

public class MemberDocumentation extends Documentation {

    @Test
    void 자신의_사용자_정보를_수정한다() {
        MemberUpdateRequest memberUpdateRequest = new MemberUpdateRequest(
            "다른이름",
            "https://avatars.githubusercontent.com/u/51393021?v=4"
        );
        given("members/edit")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .body(memberUpdateRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().put("/members/" + GithubResponses.소롱.getLogin())
            .then().log().all()
            .extract();
    }

    @Test
    void 사용자_정보를_조회한다() {
        given("members/read")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .when().get("/members/{username}", GithubResponses.소롱.getLogin())
            .then().log().all()
            .extract();
    }
}
