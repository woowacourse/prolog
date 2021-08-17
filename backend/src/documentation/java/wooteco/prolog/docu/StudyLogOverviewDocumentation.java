package wooteco.prolog.docu;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;

public class StudyLogOverviewDocumentation extends Documentation {

    @Test
    void 자신의_태그를_조회한다() {
        given("me/tags")
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/me/tags")
                .then().log().all()
                .extract();
    }
}
