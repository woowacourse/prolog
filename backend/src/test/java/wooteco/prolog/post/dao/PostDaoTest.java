package wooteco.prolog.post.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import wooteco.prolog.login.dao.MemberDao;
import wooteco.prolog.login.domain.Member;
import wooteco.prolog.login.domain.Role;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.tag.dao.TagDao;
import wooteco.prolog.tag.domain.Tag;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.setRemoveAssertJRelatedElementsFromStackTrace;

@JdbcTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PostDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private PostDao postDao;

    private static final Member FIRST_MEMBER = new Member(1L, "소롱", Role.CREW, 1L, "image");
    private static final Member SECOND_MEMBER = new Member(2L, "피카", Role.CREW, 2L, "image");

    public static final Post FIRST_POST = new Post(FIRST_MEMBER, "이것은 제목", "피케이와 포모의 포스트", 1L, Arrays.asList(new Tag("소롱의글쓰기"), new Tag("스프링")));
    public static final Post SECOND_POST = new Post(FIRST_MEMBER, "이것은 두번째 제목", "피케이와 포모의 포스트 2", 1L, Arrays.asList(new Tag("자바"), new Tag("집필왕웨지")));
    public static final Post THIRD_POST = new Post(SECOND_MEMBER, "이것은 3 제목", "피케이 포스트", 2L, Arrays.asList(new Tag("자바"), new Tag("피케이")));
    public static final Post FOURTH_POST = new Post(SECOND_MEMBER, "이것은 네번 제목", "포모의 포스트", 2L, Arrays.asList(new Tag("감자튀기기"), new Tag("집필왕웨지")));

    @BeforeEach
    void setUp() {
        postDao = new PostDao(jdbcTemplate);
        insertTestMember(FIRST_MEMBER, SECOND_MEMBER);
    }

    private void insertTestMember(Member firstMember, Member secondMember) {
        String sql = "INSERT INTO member (id, nickname, role, github_id, image_url) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, firstMember.getId(), firstMember.getNickname(), firstMember.getRole().name(), firstMember.getGithubId(), firstMember.getImageUrl());
        jdbcTemplate.update(sql, secondMember.getId(), firstMember.getNickname(), secondMember.getRole().name(), secondMember.getGithubId(), secondMember.getImageUrl());
    }

    @Test
    void 포스트를_저장하는_테스트() {
        // given
        // when
        Post expected = 포스트를_저장한다(FIRST_POST);

        // then
        assertThat(expected.getId()).isNotNull();
        assertThat(expected.getMember()).isEqualTo(FIRST_MEMBER);
        assertThat(expected.getTitle()).isEqualTo(FIRST_POST.getTitle());
        assertThat(expected.getContent()).isEqualTo(FIRST_POST.getContent());
    }

    private Post 포스트를_저장한다(Post post) {
        return postDao.insert(post);
    }

    @Test
    void 다수의_포스트를_저장하는_테스트() {
        // given
        List<Post> expectedPosts = Arrays.asList(FIRST_POST, SECOND_POST, THIRD_POST, FOURTH_POST);
        // when
        postDao.insert(expectedPosts);

        //then
        List<Post> results = postDao.findAll();

        for (int i = 0; i < results.size(); i++) {
            Post result = results.get(i);
            Post expectedPost = expectedPosts.get(i);

            assertThat(result.getContent()).isEqualTo(expectedPost.getContent());
            assertThat(result.getTitle()).isEqualTo(expectedPost.getTitle());
        }
    }

    @Test
    void 개별_포스트를_불러온다() {
        // given
        Post post = 포스트를_저장한다(SECOND_POST);

        // when
        Post foundPost = postDao.findById(post.getId());

        // then
        assertThat(foundPost.getId()).isEqualTo(post.getId());
        assertThat(foundPost.getMember()).isEqualTo(FIRST_MEMBER);
        assertThat(foundPost.getContent()).isEqualTo(SECOND_POST.getContent());
        assertThat(foundPost.getTitle()).isEqualTo(SECOND_POST.getTitle());
    }

    @Test
    void 모든_포스트를_불러온다() {
        // given
        Post 첫번째_포스트 = 포스트를_저장한다(THIRD_POST);
        Post 두번째_포스트 = 포스트를_저장한다(FOURTH_POST);

        // when
        List<Post> posts = postDao.findAll();

        // then
        assertThat(posts).contains(첫번째_포스트, 두번째_포스트);
    }
}