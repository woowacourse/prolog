package wooteco.prolog.studylog.post.domain.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.studylog.domain.*;
import wooteco.prolog.studylog.domain.repository.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostRepositoryTest {

    private static final String POST1_TITLE = "이것은 제목";
    private static final String POST2_TITLE = "이것은 두번째 제목";
    private static final String POST3_TITLE = "이것은 3 제목";
    private static final String POST4_TITLE = "이것은 네번 제목";

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private MissionRepository missionRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private PostTagRepository postTagRepository;

    private final Member member1 = new Member("이름1", "별명1", Role.CREW, 1L, "image");

    private final Member member2 = new Member("이름2", "별명2", Role.CREW, 2L, "image");

    private final Level level1 = new Level("레벨1");
    private final Level level2 = new Level("레벨2");

    private final Mission mission1 = new Mission("자동차 미션", level1);
    private final Mission mission2 = new Mission("수동차 미션", level2);

    private final Tag tag1 = new Tag("소롱의글쓰기");
    private final Tag tag2 = new Tag("스프링");
    private final Tag tag3 = new Tag("감자튀기기");
    private final Tag tag4 = new Tag("집필왕웨지");
    private final Tag tag5 = new Tag("피케이");
    private final List<Tag> tags = asList(
            tag1, tag2, tag3, tag4, tag5
    );

    private final Post post1 = new Post(member1, POST1_TITLE, "피케이와 포모의 포스트", mission1, asList(tag1, tag2));
    private final Post post2 = new Post(member1, POST2_TITLE, "피케이와 포모의 포스트 2", mission1, asList(tag2, tag3));
    private final Post post3 = new Post(member2, POST3_TITLE, "피케이 포스트", mission2, asList(tag3, tag4, tag5));
    private final Post post4 = new Post(member2, POST4_TITLE, "포모의 포스트", mission2, asList());

    @BeforeEach
    void setUp() {
        levelRepository.saveAll(asList(level1, level2));

        missionRepository.saveAll(asList(mission1, mission2));

        memberRepository.saveAll(asList(member1, member2));

        tagRepository.saveAll(tags);

        postRepository.saveAll(asList(post1, post2, post3, post4));
    }

    @DisplayName("레벨로 찾기")
    @Test
    void findWithLevel() {
        //given
        List<Long> levelIds = singletonList(level1.getId());
        Specification<Post> specs = PostSpecification.findByLevelIn(levelIds)
                .and(PostSpecification.distinct(true));

        // when
        Page<Post> posts = postRepository.findAll(specs, PageRequest.of(0, 10));

        //then
        assertThat(posts).containsExactlyInAnyOrder(post1, post2);
    }

    @DisplayName("미션으로 찾기")
    @Test
    void findWithMission() {
        //given
        List<Long> missionIds = singletonList(mission2.getId());
        Specification<Post> specs = PostSpecification.equalIn("mission", missionIds)
                .and(PostSpecification.distinct(true));

        // when
        Page<Post> posts = postRepository.findAll(specs, PageRequest.of(0, 10));

        //then
        assertThat(posts).containsExactlyInAnyOrder(post3, post4);
    }

    @DisplayName("태그로 찾기")
    @Test
    void findWithTags() {
        // given
        List<PostTag> postTags = postTagRepository.findByTagIn(asList(tag1, tag2));
        List<Long> tagIds = postTags.stream().map(it -> it.getTag().getId()).collect(Collectors.toList());
        Specification<Post> specs = PostSpecification.findByTagIn(tagIds)
                .and(PostSpecification.distinct(true));

        // when
        Page<Post> posts = postRepository.findAll(specs, PageRequest.of(0, 10));

        //then
        assertThat(posts).containsExactlyInAnyOrder(post1, post2);
    }

    @DisplayName("멤버로 찾기")
    @Test
    void findWithMembers() {
        // given
        List<String> usernames = asList(member1.getUsername(), member2.getUsername());
        Specification<Post> specs = PostSpecification.findByUsernameIn(usernames)
                .and(PostSpecification.distinct(true));

        // when
        Page<Post> posts = postRepository.findAll(specs, PageRequest.of(0, 10));

        //then
        assertThat(posts).containsExactlyInAnyOrder(post1, post2, post3, post4);
    }

    @DisplayName("레벨, 미션, 태그로 찾기")
    @Test
    void findWithLevelAndMissionAndTag() {
        //given
        List<Long> levelIds = singletonList(level1.getId());
        List<Long> missionIds = singletonList(mission1.getId());
        List<PostTag> postTags = postTagRepository.findByTagIn(asList(tag1, tag2));
        List<Long> tagIds = postTags.stream().map(it -> it.getTag().getId()).collect(Collectors.toList());
        Specification<Post> specs = PostSpecification.findByLevelIn(levelIds)
                .and(PostSpecification.equalIn("mission", missionIds))
                .and(PostSpecification.findByTagIn(tagIds))
                .and(PostSpecification.distinct(true));
        // when
        Page<Post> posts = postRepository.findAll(specs, PageRequest.of(0, 10));

        assertThat(posts).containsExactlyInAnyOrder(post1, post2);
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