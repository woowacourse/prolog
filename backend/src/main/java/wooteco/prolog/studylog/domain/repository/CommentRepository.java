package wooteco.prolog.studylog.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wooteco.prolog.studylog.domain.Comment;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.dto.CommentCount;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c"
        + " JOIN FETCH c.studylog s"
        + " JOIN FETCH c.member m"
        + " WHERE s = :studylog"
        + " AND c.isDelete = false")
    List<Comment> findCommentByStudylog(Studylog studylog);

    @Query(
        "SELECT new wooteco.prolog.studylog.domain.repository.dto.CommentCount(c.studylog, COUNT(c))"
            + " FROM Comment c"
            + " WHERE c.studylog IN :studylogs"
            + " AND c.isDelete = false"
            + " GROUP BY c.studylog")
    List<CommentCount> countByStudylogIn(@Param("studylogs") List<Studylog> studylogs);
}
