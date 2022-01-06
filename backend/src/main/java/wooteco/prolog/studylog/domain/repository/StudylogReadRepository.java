package wooteco.prolog.studylog.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.studylog.domain.StudylogRead;

import java.util.List;

public interface StudylogReadRepository extends JpaRepository<StudylogRead, Long> {
    boolean existsByMemberIdAndStudylogId(Long memberId, Long studylogId);

    List<StudylogRead> findByMemberId(Long memberId);
}
