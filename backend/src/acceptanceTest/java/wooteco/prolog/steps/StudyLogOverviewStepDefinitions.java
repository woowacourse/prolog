package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.MonthDay;
import java.time.Year;
import java.util.List;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.fixtures.GithubResponses;
import wooteco.prolog.fixtures.PostAcceptanceFixture;
import wooteco.prolog.fixtures.TagAcceptanceFixture;
import wooteco.prolog.studylog.application.dto.CalendarStudylogResponse;
import wooteco.prolog.studylog.application.dto.MemberTagResponse;

public class StudyLogOverviewStepDefinitions extends AcceptanceSteps {

    @When("{string}의 태그 목록을 조회하면")
    public void 나의태그목록을조회하면(String name) {
        final String memberName = GithubResponses.findByName(name).getLogin();
        context.invokeHttpGet("/members/" + memberName + "/tags");
    }

    @Then("해당 유저의 태그 목록이 조회된다")
    public void 나의태그목록이조회된다() {
        final List<MemberTagResponse> data = context.response.then().extract()
                .body()
                .jsonPath()
                .getList("data", MemberTagResponse.class);

        assertThat(data)
                .extracting(
                        memberTagResponse -> memberTagResponse.getTagResponse().getName(),
                        MemberTagResponse::getCount)
                .containsExactlyInAnyOrder(
                        tuple(TagAcceptanceFixture.TAG1.getTagName(), 2),
                        tuple(TagAcceptanceFixture.TAG2.getTagName(), 2),
                        tuple(TagAcceptanceFixture.TAG3.getTagName(), 2),
                        tuple(TagAcceptanceFixture.TAG4.getTagName(), 2),
                        tuple("ALL", 4)
                );
    }

    @When("{string}의 {int}년 {int}월 스터디로그 목록을 조회하면")
    public void 나의스터디로그목록을조회하면(String name, int year, int month) {
        final String memberName = GithubResponses.findByName(name).getLogin();
        String path = String.format("members/%s/calendar-studylogs?year=%d&month=%d", memberName, year, month);
        context.invokeHttpGet(path);
    }

    @When("{string}의 이번 달 스터디로그 목록을 조회하면")
    public void 이번달스터디로그목록을조회하면(String name) {
        나의스터디로그목록을조회하면(name, Year.now().getValue(), MonthDay.now().getMonth().getValue());
    }

    @Then("해당 유저의 스터디로그 목록이 조회된다")
    public void 나의스터디로그목록이조회된다() {
        final List<CalendarStudylogResponse> data = context.response.then().extract()
                .body()
                .jsonPath()
                .getList("data", CalendarStudylogResponse.class);

        assertThat(data).extracting(CalendarStudylogResponse::getTitle)
                .containsExactlyInAnyOrder(
                        PostAcceptanceFixture.POST1.getPostRequest().getTitle(),
                        PostAcceptanceFixture.POST2.getPostRequest().getTitle()
                );
    }
}
