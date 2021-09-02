package wooteco.prolog.studylog.posttag.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.application.LevelService;
import wooteco.prolog.studylog.application.MissionService;
import wooteco.prolog.studylog.application.PostService;
import wooteco.prolog.studylog.application.PostTagService;
import wooteco.prolog.studylog.application.dto.*;
import wooteco.prolog.studylog.domain.PostTag;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.support.utils.IntegrationTest;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
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
        List<TagRequest> tagRequests1 = createTagRequests("태그1", "태그2", "태그3", "태그5");
        List<TagRequest> tagRequests2 = createTagRequests("태그2", "태그3", "태그6");

        addTagRequestToPost(tagRequests1, tagRequests2);

        //when
        List<TagResponse> tags = postTagService.findTagsIncludedInPost();

        //then
        List<String> tagNames = Arrays.asList("태그1", "태그2", "태그3", "태그5", "태그6");

        List<String> expectedTagNames = tags.stream()
                .map(TagResponse::getName)
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
    @Transactional
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