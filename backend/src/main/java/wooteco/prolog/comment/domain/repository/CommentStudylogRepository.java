package wooteco.prolog.comment.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.comment.domain.CommentStudylog;

public interface CommentStudylogRepository extends JpaRepository<CommentStudylog, Long> {

    @Query("SELECT sc FROM CommentStudylog sc "
        + "JOIN FETCH sc.comment c "
        + "JOIN FETCH c.member m "
        + "WHERE c.id = :commentId "
        + "AND c.isDelete = false")
    Optional<CommentStudylog> findByCommentId(Long commentId);

    @Query("SELECT sc FROM CommentStudylog sc "
        + "JOIN FETCH sc.comment c "
        + "JOIN FETCH c.member m "
        + "WHERE sc.studylogId = :studylogId "
        + "AND c.isDelete = false")
    List<CommentStudylog> findAllByStudylogId(Long studylogId);
}
