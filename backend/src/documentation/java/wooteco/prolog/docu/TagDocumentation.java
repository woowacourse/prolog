package wooteco.prolog.docu;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static wooteco.prolog.ResponseFixture.TAG_RESPONSES;

import io.restassured.module.mockmvc.response.ValidatableMockMvcResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import wooteco.prolog.NewDocumentation;
import wooteco.prolog.studylog.application.StudylogTagService;
import wooteco.prolog.studylog.ui.TagController;

@WebMvcTest(controllers = TagController.class)
public class TagDocumentation extends NewDocumentation {

    @MockBean
    private StudylogTagService studylogTagService;

    @Test
    void 태그_목록을_조회한다() {
        //given
        given(studylogTagService.findTagsIncludedInStudylogs())
            .willReturn(TAG_RESPONSES);

        //when
        ValidatableMockMvcResponse response = given
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/tags")
            .then().log().all();

        //then
        response.expect(status().isOk());

        //docs
        response.apply(document("tag/list"));
    }
}
