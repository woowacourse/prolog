package wooteco.prolog.docu;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static wooteco.prolog.ResponseFixture.MISSION_RESPONSE1;
import static wooteco.prolog.ResponseFixture.MISSION_RESPONSE2;
import static wooteco.prolog.common.exception.BadRequestCode.DUPLICATE_MISSION;

import io.restassured.module.mockmvc.response.ValidatableMockMvcResponse;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import wooteco.prolog.NewDocumentation;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.session.application.MissionService;
import wooteco.prolog.session.application.dto.MissionRequest;
import wooteco.prolog.session.ui.MissionController;

@WebMvcTest(controllers = MissionController.class)
public class MissionDocumentation extends NewDocumentation {

    @MockBean
    private MissionService missionService;

    @Test
    void 미션_목록을_조회한다() {
        //given
        given(missionService.findAll())
            .willReturn(Arrays.asList(MISSION_RESPONSE1, MISSION_RESPONSE2));

        //when
        ValidatableMockMvcResponse response = given
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/missions")
            .then().log().all();

        //then
        response.expect(status().isOk());

        //docs
        response.apply(document("mission/list"));
    }

    @Test
    void 미션을_저장한다() {
        //given
        given(missionService.create(any()))
            .willReturn(MISSION_RESPONSE1);

        //when
        MissionRequest params = new MissionRequest("지하철 노선도 미션", 1L);
        ValidatableMockMvcResponse response = given
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .body(params)
            .when().post("/missions")
            .then().log().all();

        //then
        response.expect(status().isOk());

        //docs
        response.apply(document("mission/create/success"));
    }

    @Test
    void 미션_이름이_중복될_경우_예외처리한다() {
        //given
        doThrow(new BadRequestException(DUPLICATE_MISSION)).when(missionService)
            .create(any());

        //when
        MissionRequest params = new MissionRequest("지하철 노선도 미션", 1L);
        ValidatableMockMvcResponse response = given
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .body(params)
            .when().post("/missions")
            .then().log().all();

        //then
        response.expect(status().isBadRequest());
        response.expect(jsonPath("code").value(3001));
        response.expect(jsonPath("message").value("미션이 중복됩니다."));

        //docs
        response.apply(document("mission/create/fail"));
    }
}
