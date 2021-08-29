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
import wooteco.prolog.post.application.dto.CalendarPostResponse;
import wooteco.prolog.tag.dto.MemberTagResponse;

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
                        tuple(TagAcceptanceFixture.TAG1.getTagName(), 1),
                        tuple(TagAcceptanceFixture.TAG2.getTagName(), 1),
                        tuple(TagAcceptanceFixture.TAG3.getTagName(), 1),
                        tuple(TagAcceptanceFixture.TAG4.getTagName(), 1),
                        tuple("all", 4)
                );
    }

    @When("{string}의 {int}년 {int}월 포스트 목록을 조회하면")
    public void 나의포스트목록을조회하면(String name, int year, int month) {
        final String memberName = GithubResponses.findByName(name).getLogin();
        String path = String.format("members/%s/calendar-posts?year=%d&month=%d", memberName, year, month);
        context.invokeHttpGet(path);
    }

    @When("{string}의 이번 달 포스트 목록을 조회하면")
    public void 이번달포스트목록을조회하면(String name) {
        나의포스트목록을조회하면(name, Year.now().getValue(), MonthDay.now().getMonth().getValue());
    }

    @Then("해당 유저의 포스트 목록이 조회된다")
    public void 나의포스트목록이조회된다() {
        final List<CalendarPostResponse> data = context.response.then().extract()
                .body()
                .jsonPath()
                .getList("data", CalendarPostResponse.class);

        assertThat(data).extracting(CalendarPostResponse::getTitle)
                .containsExactlyInAnyOrder(
                        PostAcceptanceFixture.POST1.getPostRequest().getTitle(),
                        PostAcceptanceFixture.POST2.getPostRequest().getTitle()
                );
    }
}
