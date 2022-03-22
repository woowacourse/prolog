package wooteco.prolog.docu;

import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import java.util.Collections;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.prolog.NewDocumentation;
import wooteco.prolog.ability.application.AbilityService;
import wooteco.prolog.ability.application.dto.AbilityCreateRequest;
import wooteco.prolog.ability.application.dto.AbilityResponse;
import wooteco.prolog.ability.application.dto.AbilityUpdateRequest;
import wooteco.prolog.ability.application.dto.ChildAbilityResponse;
import wooteco.prolog.ability.ui.AbilityController;

@WebMvcTest(controllers = AbilityController.class)
public class AbilityDocumentation extends NewDocumentation {

    @MockBean
    private AbilityService abilityService;

    @Test
    void 기본_역량_등록() {
        doNothing().when(abilityService).applyDefaultAbilities(anyLong(), anyString());

        given.header(AUTHORIZATION, "Bearer " + accessToken)
            .when().post("/abilities/templates/be")
            .then().log().all().apply(document("abilities/create-template-be")).statusCode(HttpStatus.OK.value());

        given.header(AUTHORIZATION, "Bearer " + accessToken)
            .when().post("/abilities/templates/fe")
            .then().log().all().apply(document("abilities/create-template-fe")).statusCode(HttpStatus.OK.value());
    }

    @Test
    void 역량_생성() {
        when(abilityService.createAbility(any(), any())).thenReturn(ABILITY_RESPONSE);

        given.header(AUTHORIZATION, "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .body(ABILITY_REQUEST)
            .when().post("/abilities")
            .then().log().all().apply(document("abilities/create")).statusCode(HttpStatus.OK.value());
    }

    @Test
    void 역량_목록_조회() {
        when(abilityService.findParentAbilitiesByUsername(any())).thenReturn(ABILITY_RESPONSES);

        given
            .when().get("/members/username/abilities")
            .then().log().all().apply(document("abilities/read")).statusCode(HttpStatus.OK.value());
    }

    @Test
    void 역량_수정() {
        when(abilityService.updateAbility(any(), any(), any())).thenReturn(ABILITY_RESPONSES);

        given.header(AUTHORIZATION, "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .body(ABILITY_UPDATE_REQUEST)
            .when().put("/abilities/" + ABILITY_UPDATE_REQUEST.getId())
            .then().log().all().apply(document("abilities/update")).statusCode(HttpStatus.OK.value());
    }

    @Test
    void 역량_제거() {
        doNothing().when(abilityService).deleteAbility(any(), any());

        given.header(AUTHORIZATION, "Bearer " + accessToken)
            .when().delete("/abilities/1")
            .then().log().all().apply(document("abilities/delete")).statusCode(HttpStatus.OK.value());
    }

    private static final AbilityCreateRequest ABILITY_REQUEST = new AbilityCreateRequest(
        "역량 이름",
        "역량 설명",
        "#ffffff",
        1L);

    private static final AbilityUpdateRequest ABILITY_UPDATE_REQUEST = new AbilityUpdateRequest(
        1L,
        "역량 이름",
        "역량 설명",
        "#ffffff"
    );

    private static final AbilityResponse ABILITY_RESPONSE = new AbilityResponse(
        2L,
        "역량 이름",
        "역량 설명",
        "#001122",
        false,
        Collections.emptyList()
    );

    private static final List<AbilityResponse> ABILITY_RESPONSES = Lists.newArrayList(
        new AbilityResponse(
            1L,
            "부모 역량 이름",
            "부모 역량 설명",
            "#001122",
            true,
            Lists.newArrayList(
                new ChildAbilityResponse(
                    2L,
                    "자식 역량 이름",
                    "자식 역량 설명",
                    "#001122",
                    false
                )
            )),
        new AbilityResponse(
            3L,
            "부모2 역량 이름",
            "부모2 역량 설명",
            "#001122",
            true,
            Lists.newArrayList(
                new ChildAbilityResponse(
                    4L,
                    "자식2 역량 이름",
                    "자식2 역량 설명",
                    "#001122",
                    false
                )
            )));
}
