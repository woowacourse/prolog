package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import java.util.Optional;
import org.assertj.core.util.Lists;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.ability.application.dto.AbilityStudylogResponse;
import wooteco.prolog.ability.application.dto.HierarchyAbilityResponse;
import wooteco.prolog.ability.application.dto.StudylogAbilityRequest;
import wooteco.prolog.fixtures.GithubResponses;
import wooteco.prolog.studylog.application.dto.StudylogResponse;

public class StudylogAbilityStepDefinitions extends AcceptanceSteps {

    @When("{string} 학습로그에 {string} 역량을 맵핑하(면)(고)")
    public void 역량에학습로그를맵핑하면(String studylogName, String abilityName) {
        HierarchyAbilityResponse ability = (HierarchyAbilityResponse) context.storage.get(
            abilityName);
        StudylogResponse studylog = (StudylogResponse) context.storage.get(studylogName);

        StudylogAbilityRequest requestBody = new StudylogAbilityRequest(
            Lists.newArrayList(ability.getId()));
        context.invokeHttpPutWithToken("/studylogs/" + studylog.getId() + "/abilities",
            requestBody);
    }

    @When("{string} 학습로그에 {string}, {string} 역량을 맵핑하(면)(고)")
    public void 학습로그에역량을맵핑하면(String studylogName, String abilityName1, String abilityName2) {
        HierarchyAbilityResponse ability1 = (HierarchyAbilityResponse) context.storage.get(
            abilityName1);
        HierarchyAbilityResponse ability2 = (HierarchyAbilityResponse) context.storage.get(
            abilityName2);
        StudylogResponse studylog = (StudylogResponse) context.storage.get(studylogName);

        StudylogAbilityRequest requestBody = new StudylogAbilityRequest(
            Lists.newArrayList(ability1.getId(), ability2.getId()));
        context.invokeHttpPutWithToken("/studylogs/" + studylog.getId() + "/abilities",
            requestBody);
    }

    @Then("{string} 학습로그에 {string} 역량이 맵핑된다")
    public void 역량에학습로그가맵핑된다(String studylogName, String abilityName) {
        List<String> abilityNames = context.response.jsonPath().getList("name");
        assertThat(abilityNames.contains(abilityName)).isTrue();
    }

    @Then("{string} 학습로그에 {string}, {string} 역량이 맵핑된다")
    public void 역량에학습로그가맵핑된다(String studylogName, String abilityName1, String abilityName2) {
        List<String> abilityNames = context.response.jsonPath().getList("name");
        assertThat(abilityNames.size()).isEqualTo(2);
        assertThat(abilityNames.contains(abilityName1)).isTrue();
        assertThat(abilityNames.contains(abilityName2)).isTrue();
    }

    @When("{string}이 작성한 학습로그에 역량정보를 포함하여 조회하면")
    public void 작성한학습로그에역량정보를포함하여조회하면(String member) {
        String username = GithubResponses.findByName(member).getLogin();
        context.invokeHttpGet("/members/" + username + "/ability-studylogs");
    }

    @When("{string}이 작성한 역량이 맵핑된 학습로그를 조회하면")
    public void 작성한역량이맵핑된학습로그를조회하면(String member) {
        String username = GithubResponses.findByName(member).getLogin();
        context.invokeHttpGet("/members/" + username + "/ability-studylogs/mapping-only");
    }

    @Then("{string} 학습로그가 조회된다")
    public void 학습로그가조회된다(String studylogName) {
        List<AbilityStudylogResponse> abilityStudylogResponses = context.response.jsonPath()
            .getList("data", AbilityStudylogResponse.class);
        boolean isExist = abilityStudylogResponses.stream()
            .filter(it -> it.getStudylog().getTitle().equals(studylogName))
            .findAny()
            .isPresent();

        assertThat(isExist).isTrue();
    }

    @Then("{string}, {string} 역량이 맵핑된 {string} 학습로그가 조회된다")
    public void 역량이맵핑된학습로그가조회된다(String abilityName1, String abilityName2, String studylogName) {
        List<AbilityStudylogResponse> abilityStudylogResponses = context.response.jsonPath()
            .getList("data", AbilityStudylogResponse.class);
        Optional<AbilityStudylogResponse> abilityStudylog = abilityStudylogResponses.stream()
            .filter(it -> it.getStudylog().getTitle().equals(studylogName))
            .findAny();
        assertThat(abilityStudylogResponses.size()).isEqualTo(1);
        assertThat(abilityStudylog.isPresent()).isTrue();

        List<String> abilityNames = context.response.jsonPath().getList("data.abilities[0].name");
        assertThat(abilityNames.contains(abilityName1)).isTrue();
        assertThat(abilityNames.contains(abilityName2)).isTrue();
    }

    @Then("{string}, {string} 역량만 맵핑된 {string} 학습로그가 조회된다")
    public void 역량만맵핑된학습로그가조회된다(String abilityName1, String abilityName2, String studylogName) {
        List<AbilityStudylogResponse> abilityStudylogResponses = context.response.jsonPath()
            .getList("data", AbilityStudylogResponse.class);
        Optional<AbilityStudylogResponse> abilityStudylog = abilityStudylogResponses.stream()
            .filter(it -> it.getStudylog().getTitle().equals(studylogName))
            .findAny();
        assertThat(abilityStudylogResponses.size()).isEqualTo(1);
        assertThat(abilityStudylog.isPresent()).isTrue();
        assertThat(abilityStudylog.get().getAbilities().size()).isEqualTo(2);

        List<String> abilityNames = context.response.jsonPath().getList("data.abilities[0].name");
        assertThat(abilityNames.contains(abilityName1)).isTrue();
        assertThat(abilityNames.contains(abilityName2)).isTrue();
    }

    @Then("역량이 맵핑되지 않은 {string} 학습로그가 조회된다")
    public void 역량이맵핑되지않은학습로그가조회된다(String studylogName) {
        List<AbilityStudylogResponse> abilityStudylogResponses = context.response.jsonPath()
            .getList("data", AbilityStudylogResponse.class);
        Optional<AbilityStudylogResponse> abilityStudylog = abilityStudylogResponses.stream()
            .filter(it -> it.getStudylog().getTitle().equals(studylogName))
            .findAny();
        assertThat(abilityStudylogResponses.size()).isEqualTo(1);
        assertThat(abilityStudylog.isPresent()).isTrue();

        List<String> abilityNames = context.response.jsonPath().getList("data.abilities[0].name");
        assertThat(abilityNames.isEmpty()).isTrue();
    }
}
