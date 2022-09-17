package wooteco.prolog.comment.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.comment.domain.CommentLevellog;

public interface CommentLevellogRepository extends JpaRepository<CommentLevellog, Long> {

    @Query("SELECT sl FROM CommentLevellog sl "
        + "JOIN FETCH sl.comment c "
        + "JOIN FETCH c.member m "
        + "WHERE c.id = :commentId "
        + "AND c.isDelete = false")
    Optional<CommentLevellog> findByCommentId(Long commentId);

    @Query("SELECT sl FROM CommentLevellog sl "
        + "JOIN FETCH sl.comment c "
        + "JOIN FETCH c.member m "
        + "WHERE sl.levellogId = :levellogId "
        + "AND c.isDelete = false")
    List<CommentLevellog> findAllByLevellogId(Long levellogId);
}
