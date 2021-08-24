package wooteco.prolog.posttag.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import wooteco.prolog.level.application.LevelService;
import wooteco.prolog.level.application.dto.LevelRequest;
import wooteco.prolog.level.application.dto.LevelResponse;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.mission.application.MissionService;
import wooteco.prolog.mission.application.dto.MissionRequest;
import wooteco.prolog.mission.application.dto.MissionResponse;
import wooteco.prolog.post.application.PostService;
import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.post.application.dto.PostResponse;
import wooteco.prolog.posttag.domain.PostTag;
import wooteco.prolog.tag.domain.Tag;
import wooteco.prolog.tag.dto.TagRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestConstructor(autowireMode = AutowireMode.ALL)
class PostTagServiceTest {

    @Autowired
    private PostTagService postTagService;
    @Autowired
    private PostService postService;
    @Autowired
    private LevelService levelService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private MemberService memberService;

    private Member member;

    @BeforeEach
    void setUp() {
        LevelResponse level = levelService.create(new LevelRequest("레벨1"));
        MissionResponse mission = missionService.create(new MissionRequest("미션 이름", level.getId()));

        this.member = memberService
                .findOrCreateMember(new GithubProfileResponse("이름", "별명", "1", "image"));
    }

    @DisplayName("포스트 태그가 등록되면 포스트 태그를 찾아올 수 있는지 확인한다.")
    @Test
    public void findAllTest() {
        //given
        List<TagRequest> tagRequests1 = createTagRequests("태그1", "태그2");
        List<TagRequest> tagRequests2 = createTagRequests("태그2", "태그3");

        addTagRequestToPost(tagRequests1, tagRequests2);

        //when
        List<PostTag> postTags = postTagService.findAll();

        //then
        List<String> tagNames = Stream.concat(tagRequests1.stream(), tagRequests2.stream())
                .map(TagRequest::getName)
                .collect(toList());

        List<String> expectedTagNames = postTags.stream()
                .map(PostTag::getTag)
                .map(Tag::getName)
                .collect(toList());

        assertThat(expectedTagNames).containsExactlyElementsOf(tagNames);
    }

    @SafeVarargs
    private final List<PostResponse> addTagRequestToPost(List<TagRequest>... tagRequests) {
        List<PostRequest> posts = Arrays.stream(tagRequests)
                .map(it -> new PostRequest("이름", "별명", 1L, it))
                .collect(toList());

        return postService.insertPosts(member, posts);
    }

    private List<TagRequest> createTagRequests(String... tags) {
        return Arrays.stream(tags)
                .map(TagRequest::new)
                .collect(toList());
    }

    @DisplayName("태그를 기반으로 포스트 태그를 조회할 수 있는지 확인")
    @Test
    public void findByTags() {
        //given
        List<TagRequest> tagRequests1 = createTagRequests("태그1");
        List<TagRequest> tagRequests2 = createTagRequests("태그1", "태그2");
        List<TagRequest> tagRequests3 = createTagRequests("태그1", "태그2", "태그3");
        List<PostResponse> postResponses =
                addTagRequestToPost(tagRequests1, tagRequests2, tagRequests3);

        //when
        List<Tag> insertedTags = postResponses.stream()
                .flatMap(postResponse -> postResponse.getTags().stream())
                .map(tagResponse -> new Tag(tagResponse.getId(), tagResponse.getName()))
                .collect(toList());

        List<PostTag> postTags = postTagService.findByTags(insertedTags);

        assertThat(countTag(postTags, "태그1")).isEqualTo(3L);
        assertThat(countTag(postTags, "태그2")).isEqualTo(2L);
        assertThat(countTag(postTags, "태그3")).isEqualTo(1L);
    }

    private long countTag(List<PostTag> postTags, String tag) {
        return postTags.stream()
                .map(PostTag::getTag)
                .map(Tag::getName)
                .filter(name -> name.equals(tag))
                .count();
    }
}