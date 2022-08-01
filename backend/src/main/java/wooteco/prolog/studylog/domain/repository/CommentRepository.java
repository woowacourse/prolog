package wooteco.prolog.studylog.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.studylog.domain.Comment;
import wooteco.prolog.studylog.domain.Studylog;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c"
        + " JOIN FETCH c.studylog s"
        + " JOIN FETCH c.member m"
        + " WHERE s = :studylog")
    List<Comment> findCommentByStudylog(Studylog studylog);
}
