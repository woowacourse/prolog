package wooteco.prolog.posttag.domain.repository;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import wooteco.prolog.level.domain.Level;
import wooteco.prolog.level.domain.repository.LevelRepository;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.mission.domain.Mission;
import wooteco.prolog.mission.domain.repository.MissionRepository;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.post.domain.repository.PostRepository;
import wooteco.prolog.posttag.domain.PostTag;
import wooteco.prolog.tag.domain.Tag;
import wooteco.prolog.tag.domain.repository.TagRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostTagRepositoryTest {

    private static final Member 웨지 = new Member("sihyung92", "웨지", Role.CREW, 2222L,
            "https://avatars.githubusercontent.com/u/51393021?v=4");

    @Autowired
    private PostTagRepository postTagRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private TagRepository tagRepository;

    @DisplayName("PostTag 생성")
    @Test
    void createPostTag() {
        // given
        Member member = memberRepository.save(웨지);
        Level level = levelRepository.save(new Level("레벨1"));
        Mission mission = missionRepository.save(new Mission("미션", level));
        Tag tag = tagRepository.save(new Tag("태그"));
        Post post = postRepository.save(new Post(member, "제목", "내용", mission, Lists.emptyList()));

        // when
        PostTag postTag = new PostTag(post, tag);
        PostTag savedPostTag = postTagRepository.save(postTag);

        // then
        assertThat(savedPostTag.getId()).isNotNull();
        assertThat(savedPostTag).usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt")
                .isEqualTo(postTag);
    }

    @DisplayName("Tag 리스트와 매칭되는 PostTag 리스트 조회")
    @Test
    void findByTagIn() {
        // given
        Member member = memberRepository.save(웨지);
        Level level = levelRepository.save(new Level("레벨1"));
        Mission mission = missionRepository.save(new Mission("미션", level));
        Post post1 = postRepository.save(new Post(member, "제목1", "내용1", mission, Lists.emptyList()));
        Post post2 = postRepository.save(new Post(member, "제목2", "내용2", mission, Lists.emptyList()));

        Tag tag1 = tagRepository.save(new Tag("태그1"));
        Tag tag2 = tagRepository.save(new Tag("태그2"));

        PostTag postTag1 = postTagRepository.save(new PostTag(post1, tag1));
        PostTag postTag2 = postTagRepository.save(new PostTag(post1, tag2));
        PostTag postTag3 = postTagRepository.save(new PostTag(post2, tag2));

        // when
        List<PostTag> postTags = postTagRepository.findByTagIn(Arrays.asList(tag1, tag2));

        // then
        assertThat(postTags).usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(postTag1, postTag2, postTag3);
    }

    @DisplayName("Tag 리스트와 매칭되는 PostTag가 없을시 빈 리스트 조회")
    @Test
    void findByTagInEmpty() {
        // given
        Tag tag1 = tagRepository.save(new Tag("태그1"));
        Tag tag2 = tagRepository.save(new Tag("태그2"));

        // when
        List<PostTag> postTags = postTagRepository.findByTagIn(Arrays.asList(tag1, tag2));

        // then
        assertThat(postTags).isEmpty();
    }
}