package wooteco.prolog.post.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import wooteco.prolog.AcceptanceTest;
import wooteco.prolog.login.AuthMemberPrincipalTestArgumentResolver;
import wooteco.prolog.login.domain.Member;
import wooteco.prolog.login.domain.Role;
import wooteco.prolog.mission.application.dto.MissionRequest;
import wooteco.prolog.mission.application.dto.MissionResponse;
import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.post.application.dto.PostResponse;
import wooteco.prolog.tag.dto.TagRequest;
import wooteco.prolog.tag.dto.TagResponse;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static wooteco.prolog.Documentation.MEMBER1;


public class PostAcceptanceTest extends AcceptanceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private PostRequest firstPost;
    private PostRequest secondPost;
    private List<PostRequest> postRequests;

    private MissionRequest missionRequest1;
    private MissionRequest missionRequest2;

    @BeforeEach
    public void setUp() {
        super.setUp();

        missionRequest1 = new MissionRequest("backend 지하철 3차 미션");
        missionRequest2 = new MissionRequest("FRONTEND 지하철 3차 미션");

        Long firstmissionId = 미션_등록함(missionRequest1);
        Long secondmissionId = 미션_등록함(missionRequest2);

        firstPost = new PostRequest(
                "[자바][옵셔널] 학습log 제출합니다.",
                "옵셔널은 NPE를 배제하기 위해 만들어진 자바8에 추가된 라이브러리입니다. \n " +
                        "다양한 메소드를 호출하여 원하는 대로 활용할 수 있습니다",
                firstmissionId,
                Arrays.asList(
                        new TagRequest("자바"),
                        new TagRequest("Optional")
                )
        );

        secondPost = new PostRequest("[자바스크립트][비동기] 학습log 제출합니다.",
                "모던 JS의 fetch문, ajax라이브러리인 axios등을 통해 비동기 요청을 \n " +
                        "편하게 할 수 있습니다. 자바 최고",
                secondmissionId,
                Arrays.asList(
                        new TagRequest("자바스크립트"),
                        new TagRequest("비동기")
                )
        );

        postRequests = Arrays.asList(
                firstPost,
                secondPost
        );

        insertTestMember(MEMBER1);
    }

    private void insertTestMember(Member member) {
        String sql = "INSERT INTO member (id, nickname, role, github_id, image_url) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, member.getId(), member.getNickname(), member.getRole().name(), member.getGithubId(), member.getImageUrl());
    }

    private ExtractableResponse<Response> 글을_작성한다(List<PostRequest> postRequests) {
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
        글을_작성한다(postRequests);

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
        assertThat(extractedTitles).contains(firstPost.getTitle(), secondPost.getTitle());
    }

    @Test
    void 글_작성하기_테스트() {
        // given
        // when
        ExtractableResponse<Response> response = 글을_작성한다(postRequests);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).contains("posts/1");
    }

    @DisplayName("글 작성시 요청한 글 개수가 0개이면 예외가 발생한다.")
    @Test
    void 글_작성하기_예외_테스트() {
        // given
        // when
        ExtractableResponse<Response> response = 글을_작성한다(Collections.emptyList());

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 개별_글을_불러온다() {
        // given
        ExtractableResponse<Response> response = 글을_작성한다(postRequests);
        String path = response.header("Location");

        // when
        ExtractableResponse<Response> expected = given()
                .when()
                .get(path)
                .then()
                .extract();

        // then
        PostResponse extracted = expected.body().as(PostResponse.class);
        List<String> extractedNames = extracted.getTags().stream()
                .map(TagResponse::getName)
                .collect(Collectors.toList());
        List<String> expectedNames = firstPost.getTags().stream()
                .map(TagRequest::getName)
                .collect(Collectors.toList());

        assertThat(expected.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(extracted.getTitle()).isEqualTo(firstPost.getTitle());
        assertThat(extracted.getContent()).isEqualTo(firstPost.getContent());
        assertThat(extracted.getMission().getId()).isEqualTo(firstPost.getMissionId());
        assertThat(extractedNames).containsAll(expectedNames);
    }

    private Long 미션_등록함(MissionRequest request) {
        return given()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/missions")
                .then()
                .log().all()
                .extract()
                .as(MissionResponse.class)
                .getId();
    }
}
