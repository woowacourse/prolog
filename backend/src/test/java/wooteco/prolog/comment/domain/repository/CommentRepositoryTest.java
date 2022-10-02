package wooteco.prolog.comment.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static wooteco.prolog.comment.domain.Comment.createComment;
import static wooteco.prolog.comment.domain.CommentType.STUDY_LOG;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.comment.domain.Comment;
import wooteco.prolog.comment.domain.Content;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.support.utils.RepositoryTest;

@RepositoryTest
class CommentRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MemberRepository memberRepository;

    private Map<String, Member> members = new HashMap<>();

    @BeforeEach
    void setUp() {
        members.put("루키",
            memberRepository.save(new Member("wishoon", "루키", Role.CREW, 1L, "https://image.url")));
    }

    @AfterEach
    void afterSetUp() {
        members.clear();
    }

    @DisplayName("게시글 ID로 댓글을 생성할 수 있다.")
    @Test
    void createCommentWithPostId() {
        // given
        Member member = members.get("루키");
        Comment comment = createComment(1L, member, new Content("댓글의 내용"), STUDY_LOG);

        // when
        Comment extract = commentRepository.save(comment);

        // then
        assertThat(extract).isNotNull();
    }

    @DisplayName("댓글 ID를 통해서 댓글을 조회할 수 있다.")
    @Test
    void findByCommentId() {
        // given
        Member member = members.get("루키");
        Comment comment = commentRepository.save(
            createComment(1L, member, new Content("댓글의 내용"), STUDY_LOG));
        entityManager.clear();

        // when
        Optional<Comment> extract = commentRepository.findByCommentId(comment.getId());

        // then
        assertThat(extract).isPresent();
    }

    @DisplayName("작성된 게시글의 댓글 전체를 조회할 수 있다.")
    @Test
    void findAllByPostIdAndCommentType() {
        // given
        Member member = members.get("루키");
        commentRepository.saveAll(
            Arrays.asList(
                createComment(1L, member, new Content("댓글의 내용1"), STUDY_LOG),
                createComment(1L, member, new Content("댓글의 내용2"), STUDY_LOG),
                createComment(1L, member, new Content("댓글의 내용3"), STUDY_LOG)));
        entityManager.clear();

        // when
        List<Comment> extract = commentRepository.findAllByPostIdAndCommentType(1L, STUDY_LOG);

        // then
        assertThat(extract).hasSize(3);
    }

    @DisplayName("작성된 게시글의 댓글 중, 삭제된 댓글을 제외하고 전체를 조회할 수 있다.")
    @Test
    void excludeDeletedCommentsOfFindAllByPostIdAndCommentType() {
        // given
        Member member = members.get("루키");
        commentRepository.saveAll(
            Arrays.asList(
                createComment(1L, member, new Content("댓글의 내용1"), STUDY_LOG),
                createComment(1L, member, new Content("댓글의 내용2"), STUDY_LOG),
                createComment(1L, member, new Content("댓글의 내용3"), STUDY_LOG)));
        entityManager.clear();

        List<Comment> comments = commentRepository.findAllByPostIdAndCommentType(1L, STUDY_LOG);
        comments.get(0).delete();
        commentRepository.flush();

        // when
        List<Comment> extract = commentRepository.findAllByPostIdAndCommentType(1L, STUDY_LOG);

        // then
        assertThat(extract).hasSize(2);
    }
}
