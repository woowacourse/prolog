package wooteco.prolog.studylog.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.studylog.domain.StudylogRead;

import java.util.List;
import java.util.Optional;

public interface StudylogReadRepository extends JpaRepository<StudylogRead, Long> {

    boolean existsByMemberIdAndStudylogId(Long memberId, Long studylogId);

    Optional<StudylogRead> findByMemberIdAndStudylogId(Long memberId, Long studylogId);

    List<StudylogRead> findByMemberId(Long memberId);
}
