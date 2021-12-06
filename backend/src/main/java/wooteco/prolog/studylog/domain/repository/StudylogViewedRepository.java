package wooteco.prolog.studylog.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.studylog.domain.StudylogViewed;

import java.util.List;

public interface StudylogViewedRepository extends JpaRepository<StudylogViewed, Long> {
    boolean existsByMemberIdAndStudylogId(Long memberId, Long studylogId);

    List<StudylogViewed> findByMemberId(Long memberId);
}
