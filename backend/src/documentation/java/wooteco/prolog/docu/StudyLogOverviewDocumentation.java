package wooteco.prolog.docu;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.GithubResponses;

public class StudyLogOverviewDocumentation extends Documentation {

    @Test
    void 유저의_태그를_조회한다() {
        given("members/tags")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/{username}/tags", GithubResponses.브라운.getLogin())
                .then().log().all()
                .extract();
    }

    @Test
    void 유저의_달력_스터디로그를_조회한다() {
        given("members/calendar-posts")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/{username}/calendar-stydylogs?year=2021&month=8", GithubResponses.브라운.getLogin())
                .then().log().all()
                .extract();
    }
}
