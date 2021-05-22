package wooteco.prolog.post.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.prolog.AcceptanceTest;
import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.post.application.dto.PostResponse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class PostAcceptanceTest extends AcceptanceTest {

    private static PostRequest FIRST_POST = new PostRequest("[자바][옵셔널] 학습log 제출합니다.",
            "옵셔널은 NPE를 배제하기 위해 만들어진 자바8에 추가된 라이브러리입니다. \n " +
                    "다양한 메소드를 호출하여 원하는 대로 활용할 수 있습니다",
            "backend 지하철 3차 미션",
            Arrays.asList("자바", "Optional")
    );

    private static PostRequest SECOND_POST = new PostRequest("[자바스크립트][비동기] 학습log 제출합니다.",
            "모던 JS의 fetch문, ajax라이브러리인 axios등을 통해 비동기 요청을 \n " +
                    "편하게 할 수 있습니다. 자바 최고",
            "FRONTEND 지하철 3차 미션",
            Arrays.asList("자바스크립트", "비동기")
    );

    private ExtractableResponse<Response> 글을_작성한다() {
        List<PostRequest> postRequests = Arrays.asList(
                FIRST_POST,
                SECOND_POST
        );

        return given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(postRequests)
                .when()
                .post("/posts")
                .then()
                .extract();
    }

    @Test
    void 전체_글을_불러온다() {
        // given
        글을_작성한다();

        // when
        ExtractableResponse<Response> response = given()
                .when()
                .get("/posts")
                .then()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .log().all()
                .extract();

        // then
        List<HashMap<String, Object>> list = response.body().jsonPath().getList("");
        List<String> extractedTitles = list.stream()
                .map(map -> (String) map.get("title"))
                .collect(Collectors.toList());

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(extractedTitles).contains(FIRST_POST.getTitle(), SECOND_POST.getTitle());
    }

    @Test
    void 글_작성하기_테스트() {
        // given
        // when
        ExtractableResponse<Response> response = 글을_작성한다();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).contains("posts/1");
    }

    @Test
    void 개별_글을_불러온다() {
        // given
        ExtractableResponse<Response> response = 글을_작성한다();
        String path = response.header("Location");

        // when
        ExtractableResponse<Response> expected = given()
                .when()
                .get(path)
                .then()
                .extract();

        // then
        PostResponse extracted = expected.body().as(PostResponse.class);
        assertThat(expected.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(extracted.getTitle()).isEqualTo(FIRST_POST.getTitle());
        assertThat(extracted.getContent()).isEqualTo(FIRST_POST.getContent());
//        assertThat(extracted.getCategory()) // TODO 카테고리는 도메인 추가시 수정해야함 (현재 하드코딩 상태)
//        assertThat(extracted.getTags()). // TODO 태그는 도메인 추가시 수정해야함 (현재 하드코딩 상태)
    }
}
