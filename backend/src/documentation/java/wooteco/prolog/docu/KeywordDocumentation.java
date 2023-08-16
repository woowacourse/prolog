package wooteco.prolog.docu;

import org.elasticsearch.common.collect.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.prolog.NewDocumentation;
import wooteco.prolog.roadmap.application.KeywordService;
import wooteco.prolog.roadmap.application.dto.KeywordCreateRequest;
import wooteco.prolog.roadmap.application.dto.KeywordResponse;
import wooteco.prolog.roadmap.application.dto.KeywordUpdateRequest;
import wooteco.prolog.roadmap.application.dto.KeywordsResponse;
import wooteco.prolog.roadmap.ui.KeywordController;

import java.util.Arrays;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

@WebMvcTest(controllers = KeywordController.class)
public class KeywordDocumentation extends NewDocumentation {

    @MockBean
    private KeywordService keywordService;

    @Test
    void 키워드_생성() {
        given(keywordService.createKeyword(any(), any())).willReturn(1L);

        given
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(KEYWORD_CREATE_REQUEST)
            .when().post("/sessions/1/keywords")
            .then().log().all().apply(document("keywords/create"))
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 키워드_단일_조회() {
        given(keywordService.findKeyword(any(), any())).willReturn(KEYWORD_SINGLE_RESPONSE);

        given
            .when().get("/sessions/1/keywords/1")
            .then().log().all().apply(document("keywords/find"))
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 키워드_단일_수정() {
        doNothing().when(keywordService).updateKeyword(any(), any(), any());

        given
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(KEYWORD_UPDATE_REQUEST)
            .when().put("/sessions/1/keywords/1")
            .then().log().all().apply(document("keywords/update"))
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 키워드_단일_삭제() {
        doNothing().when(keywordService).deleteKeyword(any(), any());

        given
            .when().delete("/sessions/1/keywords/1")
            .then().log().all().apply(document("keywords/delete"))
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 세션별_키워드_목록_조회() {
        given(keywordService.findSessionIncludeRootKeywords(any())).willReturn(
            KEYWORD_SESSION_INCLUDE_MULTI_RESPONSE);

        given
            .when().get("/sessions/1/keywords")
            .then().log().all().apply(document("keywords/find-childAll"))
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 최상위_키워드의_모든_자식_키워드들의_목록_조회() {
        given(keywordService.findKeywordWithAllChild(any(), any())).willReturn(
            KEYWORD_WITH_ALL_CHILD_MULTI_RESPONSE);

        given
            .when().get("/sessions/1/keywords/1/children")
            .then().log().all().apply(document("keywords/find-with-childAll"))
            .statusCode(HttpStatus.OK.value());
    }

    private static final KeywordCreateRequest KEYWORD_CREATE_REQUEST = new KeywordCreateRequest(
        "자바",
        "자바에 대한 설명을 작성했습니다.",
        1,
        1,
        null
    );

    private static final KeywordResponse KEYWORD_SINGLE_RESPONSE = new KeywordResponse(
        1L,
        "자바",
        "자바에 대한 설명을 작성했습니다.",
        1,
        1,
        null,
        null,
        null
    );

    private static final KeywordUpdateRequest KEYWORD_UPDATE_REQUEST = new KeywordUpdateRequest(
        "자바",
        "자바에 대한 설명을 작성했습니다.",
        1,
        1,
        null
    );

    private static final KeywordsResponse KEYWORD_SESSION_INCLUDE_MULTI_RESPONSE = new KeywordsResponse(
        List.of(
            KEYWORD_SINGLE_RESPONSE,
            KEYWORD_SINGLE_RESPONSE
        )
    );

    private static final KeywordResponse KEYWORD_WITH_ALL_CHILD_MULTI_RESPONSE = new KeywordResponse(
        1L,
        "자바",
        "자바에 대한 설명을 작성했습니다.",
        1,
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
                    1L,
                    null,
                    null
                ),
                new KeywordResponse(
                    1L,
                    "Set",
                    "자바의 자료구조인 Set에 대한 설명을 작성했습니다.",
                    2,
                    1,
                    1L,
                    null,
                    null
                ))
        )
    );
}
