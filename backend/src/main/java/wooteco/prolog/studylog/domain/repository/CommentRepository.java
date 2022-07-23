package wooteco.prolog.studylog.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.studylog.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
