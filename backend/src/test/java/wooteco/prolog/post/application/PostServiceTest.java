package wooteco.prolog.post.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import wooteco.prolog.login.application.dto.MemberResponse;
import wooteco.prolog.login.domain.Member;
import wooteco.prolog.login.domain.Role;
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
    public static final Tag FIRST_TAG = new Tag(1L, "소롱의글쓰기");
    public static final Tag SECOND_TAG = new Tag(2L, "스프링");
    public static final Tag THIRD_TAG = new Tag(3L, "감자튀기기");
    public static final Tag FOURTH_TAG = new Tag(4L, "집필왕웨지");
    public static final Tag FIFTH_TAG = new Tag(5L, "피케이");
    public static final List<Tag> tags = Arrays.asList(
            FIRST_TAG, SECOND_TAG, THIRD_TAG, FOURTH_TAG, FIFTH_TAG
    );

    private static final Member FIRST_MEMBER = new Member(1L, "웨지", "wedge", Role.CREW, 123456789L, "https://www.youtube.com/watch?v=3etKkkna-f0&t=6s");
    private static final Member SECOND_MEMBER = new Member(2L, "피카", "pika", Role.CREW, 2L, "image");

    private static final Post FIRST_POST = new Post(FIRST_MEMBER, "이것은 제목", "피케이와 포모의 포스트", 1L, Arrays.asList(FIRST_TAG.getId(), SECOND_TAG.getId()));
    private static final Post SECOND_POST = new Post(FIRST_MEMBER, "이것은 두번째 제목", "피케이와 포모의 포스트 2", 1L, Arrays.asList(THIRD_TAG.getId(), FOURTH_TAG.getId()));
    private static final Post THIRD_POST = new Post(FIRST_MEMBER, "이것은 3 제목", "피케이 포스트", 2L, Arrays.asList(FOURTH_TAG.getId(), FIFTH_TAG.getId()));
    private static final Post FOURTH_POST = new Post(FIRST_MEMBER, "이것은 네번 제목", "포모의 포스트", 2L, Arrays.asList(FIRST_TAG.getId(), FIFTH_TAG.getId()));

    @Autowired
    private PostService postService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        missionService.create(new MissionRequest("자동차 1주차 미션"));
        missionService.create(new MissionRequest("로또 미션"));
        insertTestMember(FIRST_MEMBER, SECOND_MEMBER);
    }

    private void insertTestMember(Member firstMember, Member secondMember) {
        String sql = "INSERT INTO member (id, nickname, role, github_id, image_url) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, firstMember.getId(), firstMember.getNickname(), firstMember.getRole().name(), firstMember.getGithubId(), firstMember.getImageUrl());
        jdbcTemplate.update(sql, secondMember.getId(), firstMember.getNickname(), secondMember.getRole().name(), secondMember.getGithubId(), secondMember.getImageUrl());
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
                toTagRequests(FIRST_POST, tags)
        );

        PostRequest postRequest2 = new PostRequest(
                SECOND_POST.getTitle(),
                SECOND_POST.getContent(),
                SECOND_POST.getMissionId(),
                toTagRequests(SECOND_POST, tags)
        );

        PostRequest postRequest3 = new PostRequest(
                THIRD_POST.getTitle(),
                THIRD_POST.getContent(),
                THIRD_POST.getMissionId(),
                toTagRequests(THIRD_POST, tags)
        );

        PostRequest postRequest4 = new PostRequest(
                FOURTH_POST.getTitle(),
                FOURTH_POST.getContent(),
                FOURTH_POST.getMissionId(),
                toTagRequests(FOURTH_POST, tags)
        );

        //when
        List<PostRequest> postRequestsOfFirstMember = Arrays.asList(postRequest1, postRequest2);
        List<PostResponse> postResponsesOfFirstMember = postService.insertPosts(FIRST_MEMBER, postRequestsOfFirstMember);

        List<PostRequest> postRequestsOfSecondMember = Arrays.asList(postRequest3, postRequest4);
        List<PostResponse> postResponsesOfSecondMember = postService.insertPosts(SECOND_MEMBER, postRequestsOfSecondMember);

        //then
        List<String> titles = Stream.concat(postResponsesOfFirstMember.stream(), postResponsesOfSecondMember.stream())
                .map(PostResponse::getTitle)
                .collect(Collectors.toList());

        assertThat(titles).contains(
                FIRST_POST.getTitle(),
                SECOND_POST.getTitle(),
                THIRD_POST.getTitle(),
                FOURTH_POST.getTitle()
        );

        List<String> members = Stream.concat(postResponsesOfFirstMember.stream(), postResponsesOfSecondMember.stream())
                .map(PostResponse::getAuthor)
                .map(MemberResponse::getNickname)
                .collect(Collectors.toList());

        assertThat(members).contains(FIRST_MEMBER.getNickname(), SECOND_MEMBER.getNickname());
    }

    private List<TagRequest> toTagRequests(Post firstPost, List<Tag> tags) {
        return firstPost.getTagIds().stream()
                .map(it -> mapTag(tags, it))
                .collect(Collectors.toList());
    }

    private TagRequest mapTag(List<Tag> tags, Long tagId) {
        return tags.stream()
                .filter(it -> it.getId() == tagId)
                .map(it -> new TagRequest(it.getName()))
                .findFirst().orElseThrow(RuntimeException::new);
    }

    private List<TagRequest> toTagRequests(List<Tag> tags) {
        return tags.stream()
                .map(tag -> new TagRequest(tag.getName()))
                .collect(Collectors.toList());
    }
}