package wooteco.prolog.docu;

import org.junit.jupiter.api.Test;
import wooteco.prolog.Documentation;
import wooteco.prolog.GithubResponses;

public class MemberDocumentation extends Documentation {
    @Test
    void 자신의_사용자_정보를_조회한다() {
        given("members/me")
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .when().get("/members/me")
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
