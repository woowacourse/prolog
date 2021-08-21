package wooteco.prolog.post.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.tag.domain.Tag;

@JdbcTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PostDaoTest {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    private PostDao postDao;
//
//    private static final Member FIRST_MEMBER = new Member(1L, "soulg", "소롱", Role.CREW, 1L, "image");
//    private static final Member SECOND_MEMBER = new Member(2L, "pika", "피카", Role.CREW, 2L, "image");
//
//    public static final Tag FIRST_TAG = new Tag(1L, "소롱의글쓰기");
//    public static final Tag SECOND_TAG = new Tag(2L, "스프링");
//    public static final Tag THIRD_TAG = new Tag(3L, "감자튀기기");
//    public static final Tag FOURTH_TAG = new Tag(4L, "집필왕웨지");
//    public static final Tag FIFTH_TAG = new Tag(5L, "피케이");
//    public static final List<Tag> tags = Arrays.asList(
//            FIRST_TAG, SECOND_TAG, THIRD_TAG, FOURTH_TAG, FIFTH_TAG
//    );
//
//    private static final Post FIRST_POST = new Post(FIRST_MEMBER, "이것은 제목", "피케이와 포모의 포스트", 1L, Arrays.asList(FIRST_TAG.getId(), SECOND_TAG.getId()));
//    private static final Post SECOND_POST = new Post(FIRST_MEMBER, "이것은 두번째 제목", "피케이와 포모의 포스트 2", 1L, Arrays.asList(THIRD_TAG.getId(), FOURTH_TAG.getId()));
//    private static final Post THIRD_POST = new Post(FIRST_MEMBER, "이것은 3 제목", "피케이 포스트", 2L, Arrays.asList(FOURTH_TAG.getId(), FIFTH_TAG.getId()));
//    private static final Post FOURTH_POST = new Post(FIRST_MEMBER, "이것은 네번 제목", "포모의 포스트", 2L, Arrays.asList(FIRST_TAG.getId(), FIFTH_TAG.getId()));
//
//    @BeforeEach
//    void setUp() {
//        postDao = new PostDao(jdbcTemplate);
//        insertTestMember(FIRST_MEMBER, SECOND_MEMBER);
//    }
//
//    private void insertTestMember(Member firstMember, Member secondMember) {
//        String sql = "INSERT INTO member (id, nickname, role, github_id, image_url) VALUES (?, ?, ?, ?, ?)";
//
//        jdbcTemplate.update(sql, firstMember.getId(), firstMember.getNickname(), firstMember.getRole().name(), firstMember.getGithubId(), firstMember.getImageUrl());
//        jdbcTemplate.update(sql, secondMember.getId(), firstMember.getNickname(), secondMember.getRole().name(), secondMember.getGithubId(), secondMember.getImageUrl());
//    }
//
//    @Test
//    void 포스트를_저장하는_테스트() {
//        // given
//        // when
//        Post expected = 포스트를_저장한다(FIRST_POST);
//
//        // then
//        assertThat(expected.getId()).isNotNull();
//        assertThat(expected.getMember()).isEqualTo(FIRST_MEMBER);
//        assertThat(expected.getTitle()).isEqualTo(FIRST_POST.getTitle());
//        assertThat(expected.getContent()).isEqualTo(FIRST_POST.getContent());
//    }
//
//    private Post 포스트를_저장한다(Post post) {
//        return postDao.insert(post);
//    }
//
//    @Test
//    void 다수의_포스트를_저장하는_테스트() {
//        // given
//        List<Post> expectedPosts = Arrays.asList(FIRST_POST, SECOND_POST, THIRD_POST, FOURTH_POST);
//        // when
//        postDao.insert(expectedPosts);
//
//        //then
//        List<Post> results = postDao.findAll();
//
//        for (int i = 0; i < results.size(); i++) {
//            Post result = results.get(i);
//            Post expectedPost = expectedPosts.get(i);
//
//            assertThat(result.getContent()).isEqualTo(expectedPost.getContent());
//            assertThat(result.getTitle()).isEqualTo(expectedPost.getTitle());
//        }
//    }
//
//    @Test
//    void 개별_포스트를_불러온다() {
//        // given
//        Post post = 포스트를_저장한다(SECOND_POST);
//
//        // when
//        Post foundPost = postDao.findById(post.getId());
//
//        // then
//        assertThat(foundPost.getId()).isEqualTo(post.getId());
//        assertThat(foundPost.getMember()).isEqualTo(FIRST_MEMBER);
//        assertThat(foundPost.getContent()).isEqualTo(SECOND_POST.getContent());
//        assertThat(foundPost.getTitle()).isEqualTo(SECOND_POST.getTitle());
//    }
//
//    @Test
//    void 모든_포스트를_불러온다() {
//        // given
//        Post 첫번째_포스트 = 포스트를_저장한다(THIRD_POST);
//        Post 두번째_포스트 = 포스트를_저장한다(FOURTH_POST);
//
//        // when
//        List<Post> posts = postDao.findAll();
//
//        // then
//        assertThat(posts).contains(첫번째_포스트, 두번째_포스트);
//    }
}