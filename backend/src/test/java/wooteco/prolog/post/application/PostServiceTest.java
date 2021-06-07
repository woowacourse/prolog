package wooteco.prolog.post.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import wooteco.prolog.mission.application.MissionService;
import wooteco.prolog.mission.application.dto.MissionRequest;
import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.post.application.dto.PostResponse;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.tag.domain.Tag;
import wooteco.prolog.tag.dto.TagRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PostServiceTest {
    public static final Tag FIRST_TAG = new Tag("소롱의글쓰기");
    public static final Tag SECOND_TAG = new Tag("스프링");
    public static final Tag THIRD_TAG = new Tag("감자튀기기");
    public static final Tag FOURTH_TAG = new Tag("집필왕웨지");
    public static final Tag FIFTH_TAG = new Tag("피케이");

    private static final Post FIRST_POST = new Post("이것은 제목", "피케이와 포모의 포스트", 1L, Arrays.asList(FIRST_TAG, SECOND_TAG));
    private static final Post SECOND_POST = new Post("이것은 두번째 제목", "피케이와 포모의 포스트 2", 1L, Arrays.asList(THIRD_TAG, FOURTH_TAG));
    private static final Post THIRD_POST = new Post("이것은 3 제목", "피케이 포스트", 2L, Arrays.asList(FOURTH_TAG, FIFTH_TAG));
    private static final Post FOURTH_POST = new Post("이것은 네번 제목", "포모의 포스트", 2L, Arrays.asList(FIRST_TAG, FIFTH_TAG));

    @Autowired
    private PostService postService;
    @Autowired
    private MissionService missionService;

    @BeforeEach
    void setUp() {
        missionService.create(new MissionRequest("자동차 1주차 미션"));
        missionService.create(new MissionRequest("로또 미션"));
    }

    @DisplayName("필터 검색")
    @ParameterizedTest
    @MethodSource("findWithFilter")
    void findWithFilter(List<Long> missions, List<Long> tags, List<String> expectedTitles) {
        //given
        insertPosts();

        //when
        List<PostResponse> postResponsesWithFilter = postService.findPostsWithFilter(missions, tags);

        //then
        List<String> titles = postResponsesWithFilter.stream()
                .map(PostResponse::getTitle)
                .collect(Collectors.toList());

        assertThat(titles).containsAll(
                expectedTitles
        );
    }

    private static Stream<Arguments> findWithFilter() {
        return Stream.of(
                Arguments.of(Arrays.asList(1L), Collections.emptyList(), Arrays.asList(FIRST_POST.getTitle(), SECOND_POST.getTitle())),
                Arguments.of(Collections.emptyList(), Arrays.asList(1L), Arrays.asList(FIRST_POST.getTitle(), FOURTH_POST.getTitle())),
                Arguments.of(Arrays.asList(1L), Arrays.asList(1L), Arrays.asList(FIRST_POST.getTitle())),
                Arguments.of(Arrays.asList(2L), Arrays.asList(4L, 5L), Arrays.asList(THIRD_POST.getTitle(), FOURTH_POST.getTitle()))
        );
    }

    @DisplayName("포스트 여러개 삽입")
    @Test
    void insertPosts() {
        //given
        PostRequest postRequest1 = new PostRequest(
                FIRST_POST.getTitle(),
                FIRST_POST.getContent(),
                FIRST_POST.getMissionId(),
                toTagRequests(FIRST_POST.getTags())
        );

        PostRequest postRequest2 = new PostRequest(
                SECOND_POST.getTitle(),
                SECOND_POST.getContent(),
                SECOND_POST.getMissionId(),
                toTagRequests(SECOND_POST.getTags())
        );

        PostRequest postRequest3 = new PostRequest(
                THIRD_POST.getTitle(),
                THIRD_POST.getContent(),
                THIRD_POST.getMissionId(),
                toTagRequests(THIRD_POST.getTags())
        );

        PostRequest postRequest4 = new PostRequest(
                FOURTH_POST.getTitle(),
                FOURTH_POST.getContent(),
                FOURTH_POST.getMissionId(),
                toTagRequests(FOURTH_POST.getTags())
        );

        //when
        List<PostRequest> postRequests = Arrays.asList(postRequest1, postRequest2, postRequest3, postRequest4);
        List<PostResponse> postResponses = postService.insertPosts(postRequests);

        //then
        List<String> titles = postResponses.stream()
                .map(PostResponse::getTitle)
                .collect(Collectors.toList());
        assertThat(titles).contains(
                FIRST_POST.getTitle(),
                SECOND_POST.getTitle(),
                THIRD_POST.getTitle(),
                FOURTH_POST.getTitle()
        );
    }

    private List<TagRequest> toTagRequests(List<Tag> tags) {
        return tags.stream()
                .map(tag -> new TagRequest(tag.getName()))
                .collect(Collectors.toList());
    }
}