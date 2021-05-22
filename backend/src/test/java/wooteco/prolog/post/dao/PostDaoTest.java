package wooteco.prolog.post.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import wooteco.prolog.post.domain.Post;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class PostDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private PostDao postDao;

    public static final Post FIRST_POST = new Post(1L, "웨지감자", Arrays.asList("자바", "스프링"), "어려워;");
    public static final Post SECOND_POST = new Post(1L, "웨지 호호", Arrays.asList("자바", "스프링"), "어려워;");
    public static final Post THIRD_POST = new Post(1L, "스터디 로그 작성합니다.", Arrays.asList("자바", "스프링"), "너무 힘들다...졸려...");
    public static final Post FOURTH_POST = new Post(2L, "스터디 로그 작성합니다22", Arrays.asList("자바2", "스프링2"), "너무 힘들다...졸려...2");

    @BeforeEach
    void setUp() {
        postDao = new PostDao(jdbcTemplate);
    }

    @Test
    void 포스트를_저장하는_테스트() {
        // given
        // when
        Post expected = 포스트를_저장한다(FIRST_POST);

        // then
        assertThat(expected.getId()).isNotNull();
    }

    private Post 포스트를_저장한다(Post post) {
        return postDao.insert(post);
    }

    @Test
    void 개별_포스트를_불러온다() {
        // given
        Post post = 포스트를_저장한다(SECOND_POST);

        // when
        Post foundPost = postDao.findById(post.getId());

        // then
        assertThat(foundPost.getId()).isEqualTo(post.getId());
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