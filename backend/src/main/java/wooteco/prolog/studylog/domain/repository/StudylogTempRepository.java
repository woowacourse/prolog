package wooteco.prolog.studylog.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.studylog.domain.StudylogTemp;

public interface StudylogTempRepository extends JpaRepository<StudylogTemp, Long> {
    boolean existsByMemberId(Long memberId);

    StudylogTemp findByMemberId(Long memberId);

    void deleteByMemberId(Long memberId);
}
