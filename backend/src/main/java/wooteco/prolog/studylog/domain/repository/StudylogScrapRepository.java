package wooteco.prolog.studylog.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.studylog.domain.StudylogScrap;

public interface StudylogScrapRepository extends JpaRepository<StudylogScrap, Long> {

    @Query("select count(ms) from StudylogScrap ms where ms.member.id = :memberId and ms.studylog.id = :studylogId")
    int countByMemberIdAndScrapStudylogId(Long memberId, Long studylogId);

    boolean existsByMemberIdAndStudylogId(Long memberId, Long studylogId);

    Optional<StudylogScrap> findByMemberIdAndStudylogId(Long memberId, Long studylogId);

    Page<StudylogScrap> findByMemberId(Long memberId, Pageable pageable);

    List<StudylogScrap> findByMemberId(Long memberId);

}
