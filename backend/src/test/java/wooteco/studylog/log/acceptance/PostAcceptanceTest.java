package wooteco.studylog.log.acceptance;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.ExtractableResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.studylog.log.service.PostService;
import wooteco.studylog.log.web.controller.PostController;
import wooteco.studylog.log.web.controller.dto.AuthorResponse;
import wooteco.studylog.log.web.controller.dto.CategoryResponse;
import wooteco.studylog.log.web.controller.dto.PostRequest;
import wooteco.studylog.log.web.controller.dto.PostResponse;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PostAcceptanceTest {

    @Mock
    PostService postService;

    @InjectMocks
    PostController postController;

    @Test
    void 전체_글을_불러온다() {
        // given
        List<PostResponse> postResponse = Collections.singletonList(
                new PostResponse(1L,
                        new AuthorResponse(1L, "뽀모", "image"),
                        LocalDateTime.now(),
                        new CategoryResponse(1L, "미션1"),
                        "제목",
                        "내용",
                        Arrays.asList("자바", "쟈스")
                )
        );

        List<Long> expectedIds = postResponse.stream()
                .map(PostResponse::getId)
                .collect(Collectors.toList());

        // when

        // then
        ExtractableResponse<MockMvcResponse> response = RestAssuredMockMvc.given()
                .standaloneSetup(postController)
                .when()
                .get("/posts")
                .then()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .log().all()
                .extract();

        List<HashMap<String, Object>> list = response.body().jsonPath().getList("");
        List<Long> extractedIds = list.stream()
                .map(map -> (long) (int) map.get("id"))
                .collect(Collectors.toList());

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(extractedIds).containsAll(expectedIds);
    }

    @Test
    void 글_작성하기() {
        // given
        List<PostRequest> postRequest = Arrays.asList(new PostRequest(1L,
                "title",
                Arrays.asList("자바", "쟈스"),
                "글 내용"
        ));

        PostResponse expectedResult = new PostResponse(1L,
                new AuthorResponse(1L, "뽀모", "image"),
                LocalDateTime.now(),
                new CategoryResponse(1L, "미션1"),
                "제목",
                "내용",
                Arrays.asList("자바", "쟈스"));


        // when

        // then
        ExtractableResponse<MockMvcResponse> response = RestAssuredMockMvc.given()
                .standaloneSetup(postController)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(postRequest)
                .when()
                .post("/posts")
                .then()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .log().all()
                .extract();

        PostResponse extracted = response.body().as(PostResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(extracted).isEqualTo(expectedResult);
    }

    @Test
    void 개별_글을_불러온다() {
        // given
        Long logId = 1L;
        PostResponse postResponse =
                new PostResponse(1L,
                        new AuthorResponse(1L, "웨지", "image"),
                        LocalDateTime.now(),
                        new CategoryResponse(1L, "미션1"),
                        "매감",
                        "매서운감자",
                        Arrays.asList("자바", "자스")

                );

        // when

        // then
        ExtractableResponse<MockMvcResponse> response = RestAssuredMockMvc.given()
                .standaloneSetup(postController)
                .when()
                .get("/posts/" + logId)
                .then()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .log().all()
                .extract();

        PostResponse extracted = response.body().as(PostResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(extracted).isEqualTo(postResponse);
    }

    @Test
    void 카테고리를_불러온다() {
        // given
        List<CategoryResponse> categoryResponses = Arrays.asList(
                new CategoryResponse(1L, "빈지모"),
                new CategoryResponse(2L, "빈포모"),
                new CategoryResponse(3L, "웨지노")
        );

        List<String> expectedNames = categoryResponses.stream()
                .map(CategoryResponse::getName)
                .collect(Collectors.toList());

        // when

        // then
        ExtractableResponse<MockMvcResponse> response = RestAssuredMockMvc.given()
                .standaloneSetup(postController)
                .when()
                .get("/posts/categories")
                .then()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .log().all()
                .extract();

        List<HashMap<String, Object>> list = response.body().jsonPath().getList("");
        List<String> extractedName = list.stream()
                .map(map -> (String) map.get("name"))
                .collect(Collectors.toList());

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(extractedName).containsAll(expectedNames);
    }
}
