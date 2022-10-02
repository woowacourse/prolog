package wooteco.prolog.comment.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.comment.domain.Comment;
import wooteco.prolog.comment.domain.CommentType;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c "
        + "JOIN FETCH c.member m "
        + "WHERE c.id = :commentId "
        + "AND c.isDelete = false")
    Optional<Comment> findByCommentId(Long commentId);

    @Query("SELECT c FROM Comment c "
        + "JOIN FETCH c.member m "
        + "WHERE c.postId = :postId "
        + "AND c.commentType = :commentType "
        + "AND c.isDelete = false")
    List<Comment> findAllByPostIdAndCommentType(Long postId, CommentType commentType);
}
