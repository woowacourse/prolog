package wooteco.prolog.post.application;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.mission.application.MissionService;
import wooteco.prolog.mission.application.dto.MissionRequest;
import wooteco.prolog.mission.application.dto.MissionResponse;
import wooteco.prolog.mission.domain.Mission;
import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.post.application.dto.PostResponse;
import wooteco.prolog.post.application.dto.PostsResponse;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.tag.domain.Tag;
import wooteco.prolog.tag.dto.TagRequest;
import wooteco.prolog.tag.dto.TagResponse;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PostServiceTest {

    private static final String POST1_TITLE = "이것은 제목";
    private static final String POST2_TITLE = "이것은 두번째 제목";
    private static final String POST3_TITLE = "이것은 3 제목";
    private static final String POST4_TITLE = "이것은 네번 제목";

    private static Tag tag1 = new Tag(1L, "소롱의글쓰기");
    private static Tag tag2 = new Tag(2L, "스프링");
    private static Tag tag3 = new Tag(3L, "감자튀기기");
    private static Tag tag4 = new Tag(4L, "집필왕웨지");
    private static Tag tag5 = new Tag(5L, "피케이");
    private static List<Tag> tags = asList(
            tag1, tag2, tag3, tag4, tag5
    );

    @Autowired
    private PostService postService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private MemberService memberService;

    private Member member1;
    private Member member2;

    private Mission mission1;
    private Mission mission2;

    private Post post1;
    private Post post2;
    private Post post3;
    private Post post4;

    @BeforeEach
    void setUp() {
        MissionResponse missionResponse1 = missionService.create(new MissionRequest("자동차 미션"));
        MissionResponse missionResponse2 = missionService.create(new MissionRequest("수동차 미션"));

        this.mission1 = new Mission(missionResponse1.getId(), missionResponse1.getName());
        this.mission2 = new Mission(missionResponse2.getId(), missionResponse2.getName());

        this.member1 = memberService.findOrCreateMember(new GithubProfileResponse("이름1", "별명1", "1", "image"));
        this.member2 = memberService.findOrCreateMember(new GithubProfileResponse("이름2", "별명2", "2", "image"));

        this.post1 = new Post(member1, POST1_TITLE, "피케이와 포모의 포스트", mission1, asList(tag1, tag2));
        this.post2 = new Post(member1, POST2_TITLE, "피케이와 포모의 포스트 2", mission1, asList(tag2, tag3));
        this.post3 = new Post(member2, POST3_TITLE, "피케이 포스트", mission2, asList(tag3, tag4, tag5));
        this.post4 = new Post(member2, POST4_TITLE, "포모의 포스트", mission2);
    }

    @DisplayName("포스트 여러개 삽입")
    @Test
    void insertPostsTest() {
        //given
        List<PostResponse> postsOfMember1 = insertPosts(member1, post1, post2);
        List<PostResponse> postsOfMember2 = insertPosts(member2, post3, post4);
        //when
        //then
        List<String> titles = Stream.concat(postsOfMember1.stream(), postsOfMember2.stream())
                .map(PostResponse::getTitle)
                .collect(toList());

        assertThat(titles).contains(
                post1.getTitle(),
                post2.getTitle(),
                post3.getTitle(),
                post4.getTitle()
        );

        List<String> members = Stream.concat(postsOfMember1.stream(), postsOfMember2.stream())
                .map(PostResponse::getAuthor)
                .map(MemberResponse::getNickname)
                .collect(toList());

        assertThat(members).contains(member1.getNickname(), member2.getNickname());
    }

    @DisplayName("필터 검색")
    @ParameterizedTest
    @MethodSource
    void findWithFilter(List<Long> missionIds, List<Long> tagIds, List<String> expectedPostTitles) {
        //given
        insertPosts(member1, post1, post2);
        insertPosts(member2, post3, post4);

        PostsResponse postResponsesWithFilter =
                postService.findPostsWithFilter(missionIds, tagIds, PageRequest.of(0,10));

        List<String> titles = postResponsesWithFilter.getData().stream()
                .map(PostResponse::getTitle)
                .collect(toList());

        assertThat(titles).containsExactlyInAnyOrderElementsOf(
                expectedPostTitles
        );
    }

    private static Stream<Arguments> findWithFilter() {
        return Stream.of(
                Arguments.of(emptyList(), asList(tag1.getId(), tag2.getId(), tag3.getId()), asList(POST1_TITLE, POST2_TITLE, POST3_TITLE)),
                Arguments.of(emptyList(), singletonList(tag2.getId()), asList(POST1_TITLE, POST2_TITLE)),
                Arguments.of(emptyList(), singletonList(tag3.getId()), asList(POST2_TITLE, POST3_TITLE)),
                Arguments.of(singletonList(1L), emptyList(), asList(POST1_TITLE, POST2_TITLE)),
                Arguments.of(singletonList(2L), emptyList(), asList(POST3_TITLE, POST4_TITLE)),
                Arguments.of(singletonList(1L), singletonList(tag1.getId()), singletonList(POST1_TITLE)),
                Arguments.of(singletonList(1L), asList(tag1.getId(), tag2.getId(), tag3.getId()), asList(POST1_TITLE, POST2_TITLE)),
                Arguments.of(singletonList(1L), singletonList(tag2.getId()), asList(POST1_TITLE, POST2_TITLE)),
                Arguments.of(asList(1L, 2L), singletonList(tag3.getId()), asList(POST2_TITLE, POST3_TITLE)),
                Arguments.of(emptyList(), emptyList(), asList(POST1_TITLE, POST2_TITLE, POST3_TITLE, POST4_TITLE))
        );
    }

    @DisplayName("유저 이름으로 포스트를 조회한다.")
    @Test
    void findPostsOfTest(){
        insertPosts(member1, post1, post2);
        insertPosts(member2, post3, post4);

        PostsResponse postsResponseOfMember1 = postService.findPostsOf(member1.getUsername(), Pageable.unpaged());
        PostsResponse postsResponseOfMember2 = postService.findPostsOf(member2.getUsername(), Pageable.unpaged());

        List<String> expectedResultOfMember1 = postsResponseOfMember1.getData().stream()
                .map(PostResponse::getTitle)
                .collect(toList());
        List<String> expectedResultOfMember2 = postsResponseOfMember2.getData().stream()
                .map(PostResponse::getTitle)
                .collect(toList());

 //       assertThat(expectedResultOfMember1).containsExactly(post1.getTitle(), post2.getTitle());
        assertThat(expectedResultOfMember2).containsExactly(post3.getTitle(), post4.getTitle());
    }

    @DisplayName("유저 이름으로 포스트를 조회한다 - 페이징")
    @ParameterizedTest
    @MethodSource("findPostsOfPagingTest")
    void findPostsOfPagingTest(PageRequest pageRequest, int expectedSize){
        List<Post> largePosts = IntStream.range(0, 50)
                .mapToObj(it -> post1)
                .collect(toList());

        insertPosts(member1, largePosts);

        PostsResponse postsResponseOfMember1 = postService.findPostsOf(member1.getUsername(), pageRequest);

        assertThat(postsResponseOfMember1.getData().size()).isEqualTo(expectedSize);
    }

    private static Stream<Arguments> findPostsOfPagingTest() {
        return Stream.of(
                Arguments.of(PageRequest.of(0,10), 10),
                Arguments.of(PageRequest.of(0,20), 20),
                Arguments.of(PageRequest.of(0,60), 50),
                Arguments.of(PageRequest.of(3, 15), 5),
                Arguments.of(PageRequest.of(1, 50), 0),
                Arguments.of(PageRequest.of(4, 11), 6)
        );
    }

    @DisplayName("포스트를 수정한다")
    @Test
    void updatePostTest(){
        //given
        List<PostResponse> postResponses = insertPosts(member1, post1);
        PostResponse targetPost = postResponses.get(0);
        PostRequest updatePostRequest = new PostRequest("updateTitle", "updateContent", 2L,
                toTagRequests(tags));

        //when
        postService.updatePost(member1, targetPost.getId(), updatePostRequest);

        //then
        PostResponse expectedResult = postService.findById(targetPost.getId());
        List<String> updateTagNames = tags.stream()
                .map(Tag::getName)
                .collect(toList());

        List<String> expectedTagNames = expectedResult.getTags().stream()
                .map(TagResponse::getName)
                .collect(toList());

        assertThat(expectedResult.getTitle()).isEqualTo(updatePostRequest.getTitle());
        assertThat(expectedResult.getContent()).isEqualTo(updatePostRequest.getContent());
        assertThat(expectedResult.getMission().getId()).isEqualTo(updatePostRequest.getMissionId());
        assertThat(expectedTagNames).isEqualTo(updateTagNames);
    }

    @DisplayName("포스트를 삭제한다")
    @Test
    void deletePostTest(){
        //given
        List<PostResponse> postResponses = insertPosts(member1, post1, post2, post3, post4);

        //when
        List<Long> postIds = postResponses.stream()
                .map(PostResponse::getId)
                .collect(toList());

        Long removedId = postIds.remove(0);
        postService.deletePost(member1, removedId);

        //then
        PostsResponse postsResponse = postService.findPostsOf(member1.getUsername(), Pageable.unpaged());

        List<Long> expectedIds = postsResponse.getData().stream()
                .map(PostResponse::getId)
                .collect(toList());

        assertThat(expectedIds).containsExactlyElementsOf(postIds);
    }

    private List<PostResponse> insertPosts(Member member, List<Post> posts) {
        List<PostRequest> postRequests = posts.stream()
                .map(post ->
                        new PostRequest(
                                post.getTitle(),
                                post.getContent(),
                                post.getMission().getId(),
                                toTagRequests(post)
                        )
                )
                .collect(toList());

        return postService.insertPosts(member, postRequests);
    }

    private List<PostResponse> insertPosts(Member member, Post... posts){
        return insertPosts(member, asList(posts));
    }

    private List<TagRequest> toTagRequests(Post post) {
        return post.getPostTags().stream()
                .map(postTag -> new TagRequest(postTag.getTag().getName()))
                .collect(toList());
    }

    private List<TagRequest> toTagRequests(List<Tag> tags) {
        return tags.stream()
                .map(tag -> new TagRequest(tag.getName()))
                .collect(toList());
    }
}