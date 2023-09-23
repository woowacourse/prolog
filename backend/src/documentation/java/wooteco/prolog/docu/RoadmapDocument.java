package wooteco.prolog.docu;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import wooteco.prolog.NewDocumentation;
import wooteco.prolog.roadmap.application.RoadMapService;
import wooteco.prolog.roadmap.application.dto.KeywordResponse;
import wooteco.prolog.roadmap.application.dto.KeywordsResponse;
import wooteco.prolog.roadmap.ui.RoadmapController;

@WebMvcTest(RoadmapController.class)
public class RoadmapDocument extends NewDocumentation {

    @MockBean
    private RoadMapService roadMapService;

    @Test
    void 로드맵_검색() {
        given(roadMapService.findAllKeywordsWithProgress(any(), any()))
            .willReturn(KEYWORD_SESSION_INCLUDE_MULTI_RESPONSE);

        given
            .param("curriculumId", 1)
            .when().get("/roadmaps")
            .then().log().all().apply(document("roadmap/find"))
            .statusCode(HttpStatus.OK.value());
    }

    private static final KeywordResponse KEYWORD_WITH_ALL_CHILD_MULTI_RESPONSE = new KeywordResponse(
        1L,
        "자바",
        "자바에 대한 설명을 작성했습니다.",
        1,
        1,
        3,
        1,
        null,
        null,
        new HashSet<>(
            Arrays.asList(
                new KeywordResponse(
                    2L,
                    "List",
                    "자바의 자료구조인 List에 대한 설명을 작성했습니다.",
                    1,
                    1,
                    3,
                    1,
                    1L,
                    Collections.emptyList(),
                    Collections.emptySet()
                ),
                new KeywordResponse(
                    1L,
                    "Set",
                    "자바의 자료구조인 Set에 대한 설명을 작성했습니다.",
                    2,
                    1,
                    3,
                    1,
                    1L,
                    Collections.emptyList(),
                    Collections.emptySet()
                ))
        )
    );

    private static final KeywordResponse KEYWORD_SINGLE_RESPONSE = new KeywordResponse(
        1L,
        "자바",
        "자바에 대한 설명을 작성했습니다.",
        1,
        1,
        3,
        2,
        null,
        null,
        null
    );

    private static final KeywordsResponse KEYWORD_SESSION_INCLUDE_MULTI_RESPONSE = new KeywordsResponse(
        Arrays.asList(
            KEYWORD_WITH_ALL_CHILD_MULTI_RESPONSE,
            KEYWORD_SINGLE_RESPONSE
        )
    );
}
