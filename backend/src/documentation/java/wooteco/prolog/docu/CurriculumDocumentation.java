package wooteco.prolog.docu;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import wooteco.prolog.NewDocumentation;
import wooteco.prolog.roadmap.application.CurriculumService;
import wooteco.prolog.roadmap.application.dto.CurriculumRequest;
import wooteco.prolog.roadmap.application.dto.CurriculumResponse;
import wooteco.prolog.roadmap.application.dto.CurriculumResponses;
import wooteco.prolog.roadmap.ui.CurriculumController;


@WebMvcTest(controllers = CurriculumController.class)
public class CurriculumDocumentation extends NewDocumentation {

    private static final String UTF8_JSON_TYPE = "application/json;charset=UTF-8";

    @MockBean
    CurriculumService curriculumService;

    @Test
    void 커리큘럼_생성() {
        given(curriculumService.create(any())).willReturn(1L);

        given
            .contentType(UTF8_JSON_TYPE)
            .body(CURRICULUM_REQUEST)
            .when().post("/curriculums")
            .then().log().all().apply(document("curriculums/create"))
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 커리큘럼_조회() {
        given(curriculumService.findCurriculums()).willReturn(CURRICULUMS_RESPONSE);

        given
            .contentType(UTF8_JSON_TYPE)
            .body(CURRICULUM_REQUEST)
            .when().get("/curriculums")
            .then().log().all().apply(document("curriculums/find"))
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 커리큘럼_수정() {
        doNothing().when(curriculumService).update(any(), any());

        given
            .contentType(UTF8_JSON_TYPE)
            .body(CURRICULUM_EDIT_REQUEST)
            .when().put("/curriculums/{curriculumId}", 1)
            .then().log().all().apply(document("curriculums/update"))
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 커리큘럼_삭제() {
        doNothing().when(curriculumService).delete(any());

        given
            .contentType(UTF8_JSON_TYPE)
            .when().delete("/curriculums/{curriculumId}", 1)
            .then().log().all().apply(document("curriculums/delete"))
            .statusCode(HttpStatus.NO_CONTENT.value());
    }


    private static final CurriculumRequest CURRICULUM_REQUEST = new CurriculumRequest(
        "수달이 슬로를 위해 만든 커리큘럼");

    private static final CurriculumRequest CURRICULUM_EDIT_REQUEST = new CurriculumRequest(
        "수달이 에덴을 위해 수정한 커리큘럼");

    private static final CurriculumResponses CURRICULUMS_RESPONSE = new CurriculumResponses(
        Arrays.asList(new CurriculumResponse(1L, "이스트 전용 커리큘럼"),
            new CurriculumResponse(1L, "동키콩 전용 커리큘럼")));
}
