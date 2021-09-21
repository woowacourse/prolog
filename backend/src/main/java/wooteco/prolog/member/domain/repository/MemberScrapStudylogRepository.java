package wooteco.prolog.member.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.member.domain.MemberScrapStudylog;

public interface MemberScrapStudylogRepository extends JpaRepository<MemberScrapStudylog, Long> {

    @Query("select count(ms) from MemberScrapStudylog ms where ms.member.id = :memberId and ms.scrapStudylog.id = :studylogId")
    int countByMemberIdAndScrapStudylogId(Long memberId, Long studylogId);

    Optional<MemberScrapStudylog> findByMemberIdAndScrapStudylogId(Long memberId, Long studylogId);
}
