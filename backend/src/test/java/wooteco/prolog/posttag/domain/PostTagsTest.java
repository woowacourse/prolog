package wooteco.prolog.posttag.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.level.domain.Level;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.mission.domain.Mission;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.tag.domain.Tag;

public class PostTagsTest {

    private static final Member 웨지 = new Member("sihyung92", "웨지", Role.CREW, 2222L,
        "https://avatars.githubusercontent.com/u/51393021?v=4");
    private static final Post 웨지가_쓴_글 = new Post(웨지, "제목", "내용", new Level("레벨1"), new Mission("[BE] 글쓰기 미션"), Lists.emptyList());
    private static final Tag 워니_태그 = new Tag("워니");
    private static final PostTag 워니_포스트태그 = new PostTag(1L, 웨지가_쓴_글, 워니_태그);

    private PostTags postTags;

    @BeforeEach
    void setUp() {
        this.postTags = new PostTags();
        this.postTags.add(Collections.singletonList(워니_포스트태그));
    }

    @DisplayName("PostTag가 add 될 때 중복된 이름의 Tag는 배제된다.")
    @Test
    void addTest() {
        // given
        Tag 워니_태그 = new Tag("워니");
        Tag 레벨1_태그 = new Tag("레벨1");

        PostTag 워니_포스트태그 = new PostTag(2L, 웨지가_쓴_글, 워니_태그);
        PostTag 레벨1_포스트태그 = new PostTag(3L, 웨지가_쓴_글, 레벨1_태그);

        List<PostTag> postTags = Arrays.asList(워니_포스트태그, 레벨1_포스트태그);

        // when
        this.postTags.add(postTags);

        // then
        assertThat(this.postTags.getValues()).containsExactlyInAnyOrder(this.워니_포스트태그, 레벨1_포스트태그);
    }

    @DisplayName("update로 PostTag 리스트를 교체한다.")
    @Test
    void updateTest() {
        // given
        Tag 포비_태그 = new Tag("포비");
        Tag 레벨1_태그 = new Tag("레벨1");

        PostTag 포비_포스트태그 = new PostTag(2L, 웨지가_쓴_글, 포비_태그);
        PostTag 레벨1_포스트태그 = new PostTag(3L, 웨지가_쓴_글, 레벨1_태그);

        List<PostTag> postTags = Arrays.asList(포비_포스트태그, 레벨1_포스트태그);

        // when
        this.postTags.update(postTags);

        // then
        assertThat(this.postTags.getValues()).containsExactlyInAnyOrder(포비_포스트태그, 레벨1_포스트태그);
    }
}
