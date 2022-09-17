package wooteco.prolog.comment.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.comment.domain.Comment;
import wooteco.prolog.comment.domain.CommentStudylog;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.support.utils.RepositoryTest;

@RepositoryTest
class CommentStudylogRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CommentStudylogRepository commentStudylogRepository;
    @Autowired
    private MemberRepository memberRepository;

    private Map<String, Object> data = new HashMap<>();

    @BeforeEach
    void beforeSetUp() {
        data.put("루키", memberRepository.save(new Member("wishoon", "루키", Role.CREW, 1L, "https://image.url")));
    }

    @AfterEach
    void afterSetUp() {
        data.clear();
    }

    @Test
    @DisplayName("스터디로그의 댓글을 등록할 수 있다.")
    void save() {
        // given
        CommentStudylog actual = new CommentStudylog(
            null,
            1L,
            new Comment(null, (Member) data.get("루키"), "루키의 스터디로그의 내용"));

        // when
        CommentStudylog extract = commentStudylogRepository.save(actual);

        // then
        assertThat(extract).isNotNull();
    }

    @Test
    @DisplayName("스터디로그의 댓글을 조회할 수 있다.")
    void findAllByStudylogId() {
        // given
        CommentStudylog actual = new CommentStudylog(
            null,
            1L,
            new Comment(null, (Member) data.get("루키"), "루키 스터디로그의 내용"));
        commentStudylogRepository.save(actual);
        entityManager.clear();

        // when
        List<CommentStudylog> extract = commentStudylogRepository.findAllByStudylogId(1L);

        // then
        assertAll(
            () -> assertThat(extract).hasSize(1),
            () -> assertThat(extract.get(0).getComment()).isNotNull()
        );
    }
}
