package wooteco.prolog.post.domain.repository;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.mission.domain.Mission;
import wooteco.prolog.mission.domain.repository.MissionRepository;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.posttag.domain.PostTag;
import wooteco.prolog.posttag.domain.repository.PostTagRepository;
import wooteco.prolog.tag.domain.Tag;
import wooteco.prolog.tag.domain.repository.TagRepository;

@DataJpaTest
class PostRepositoryTest {

    private static final String POST1_TITLE = "이것은 제목";
    private static final String POST2_TITLE = "이것은 두번째 제목";
    private static final String POST3_TITLE = "이것은 3 제목";
    private static final String POST4_TITLE = "이것은 네번 제목";

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MissionRepository missionRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private PostTagRepository postTagRepository;

    private final Member member1 = new Member("이름1", "별명1", Role.CREW ,1L, "image");;
    private final Member member2 = new Member("이름2", "별명2", Role.CREW ,2L, "image");;

    private final Mission mission1 = new Mission("자동차 미션");
    private final Mission mission2 = new Mission("수동차 미션");

    private final Tag tag1 = new Tag("소롱의글쓰기");
    private final Tag tag2 = new Tag( "스프링");
    private final Tag tag3 = new Tag( "감자튀기기");
    private final Tag tag4 = new Tag( "집필왕웨지");
    private final Tag tag5 = new Tag( "피케이");
    private final List<Tag> tags = asList(
            tag1, tag2, tag3, tag4, tag5
    );

    private final Post post1 = new Post(member1, POST1_TITLE, "피케이와 포모의 포스트", mission1, asList(tag1, tag2));
    private final Post post2 = new Post(member1, POST2_TITLE, "피케이와 포모의 포스트 2", mission1, asList(tag2, tag3));
    private final Post post3 = new Post(member2, POST3_TITLE, "피케이 포스트", mission2, asList(tag3, tag4, tag5));
    private final Post post4 = new Post(member2, POST4_TITLE, "포모의 포스트", mission2);

    @BeforeEach
    void setUp() {
        missionRepository.saveAll(asList(mission1, mission2));

        memberRepository.saveAll(asList(member1, member2));

        tagRepository.saveAll(tags);

        postRepository.saveAll(asList(post1, post2, post3, post4));
    }

    @DisplayName("미션과 포스트태그 목록으로 포스트를 찾을 수 있는지 확인")
    @Test
    void findDistinctByMissionInAndPostTagsInTest() {
        //given
        //when
        List<PostTag> postTags = postTagRepository.findByTagIn(singletonList(tag3));
        Page<Post> expectedResult = postRepository
                .findDistinctByMissionInAndPostTagsValuesIn(singletonList(mission1), postTags, PageRequest.of(0, 10));
        //then
        assertThat(expectedResult.getContent()).containsExactlyInAnyOrder(post2);
    }

    @DisplayName("포스트태그 목록으로 포스트를 찾을 수 있는지 확인")
    @Test
    void findDistinctByPostTagsInTest() {
        //given
        //when
        List<PostTag> postTags = postTagRepository.findByTagIn(asList(tag1, tag2));
        Page<Post> expectedResult = postRepository
                .findDistinctByPostTagsValuesIn(postTags, PageRequest.of(0, 10));
        //then
        assertThat(expectedResult.getContent()).containsExactlyInAnyOrder(post1, post2);
    }

    @DisplayName("미션 목록으로 포스트를 찾는 테스트")
    @Test
    void findByMissionInTest() {
        //given
        //when
        Page<Post> expectedResult = postRepository
                .findByMissionIn(singletonList(mission2), PageRequest.of(0, 10));
        //then
        assertThat(expectedResult.getContent()).containsExactlyInAnyOrder(post3, post4);
    }

    @DisplayName("멤버로 포스트를 찾아오는지 테스트")
    @Test
    void findByMemberTest() {
        //given
        //when
        Page<Post> expectedResult = postRepository
                .findByMember(member1, PageRequest.of(0, 10));
        //then
        assertThat(expectedResult.getContent()).containsExactlyInAnyOrder(post1, post2);
    }
}