package wooteco.prolog.docu.ability;

import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.prolog.NewDocumentation;
import wooteco.prolog.ability.application.StudylogAbilityService;
import wooteco.prolog.ability.application.dto.AbilityResponse;
import wooteco.prolog.ability.application.dto.AbilityStudylogResponse;
import wooteco.prolog.ability.application.dto.StudylogAbilityRequest;
import wooteco.prolog.ability.ui.StudylogAbilityController;
import wooteco.prolog.common.PageableResponse;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.TagResponse;

@WebMvcTest(controllers = StudylogAbilityController.class)
public class StudylogAbilityDocumentation extends NewDocumentation {

    @MockBean
    private StudylogAbilityService studylogAbilityService;

    @Test
    void 학습로그_역량_갱신() {
        when(studylogAbilityService.updateStudylogAbilities(any(), any(), any())).thenReturn(ABILITY_RESPONSES);

        given.header(AUTHORIZATION, "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .body(STUDYLOG_ABILITY_REQUEST)
            .when().put("/studylogs/" + 1L + "/abilities")
            .then().log().all().apply(document("abilities/studylog-update")).statusCode(HttpStatus.OK.value());
    }

    @Test
    void 역량포함_모든_학습로그_조회() {
        when(studylogAbilityService.findAbilityStudylogsByAbilityIds(anyString(), any(), any())).thenReturn(ABILITY_STUDYLOG_RESPONSES);

        given
            .when().get("/members/username/ability-studylogs?abilityIds=1")
            .then().log().all().apply(document("abilities/studylog-list")).statusCode(HttpStatus.OK.value());
    }

    private static final StudylogAbilityRequest STUDYLOG_ABILITY_REQUEST = new StudylogAbilityRequest(Lists.newArrayList(1L, 4L));

    private static final List<AbilityResponse> ABILITY_RESPONSES = Lists.newArrayList(
        new AbilityResponse(
            1L,
            "부모 역량 이름",
            "부모 역량 설명",
            "#001122",
            true
        ),
        new AbilityResponse(
            4L,
            "자식2 역량 이름",
            "자식2 역량 설명",
            "#001122",
            false
        )
    );

    private static final PageableResponse<AbilityStudylogResponse> ABILITY_STUDYLOG_RESPONSES = new PageableResponse(
        Lists.newArrayList(
            new AbilityStudylogResponse(
                new StudylogResponse(
                    1L,
                    new MemberResponse(),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    new MissionResponse(),
                    "제목",
                    "내용내용내용내용내용",
                    Lists.newArrayList(new TagResponse(1L, "태그1"), new TagResponse(2L, "태그2")),
                    false,
                    false,
                    10,
                    false,
                    1
                ),
                ABILITY_RESPONSES
            )
        ), 1L, 1, 1);
}
