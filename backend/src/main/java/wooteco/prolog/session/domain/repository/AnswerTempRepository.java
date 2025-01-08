package wooteco.prolog.session.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.session.domain.AnswerTemp;

public interface AnswerTempRepository extends JpaRepository<AnswerTemp, Long> {

    boolean existsByMemberId(Long memberId);

    void deleteByMemberId(Long memberId);

    List<AnswerTemp> findByMemberId(Long memberId);
}
