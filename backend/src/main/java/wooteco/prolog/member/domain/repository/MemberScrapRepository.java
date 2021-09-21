package wooteco.prolog.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.member.domain.MemberScrap;

public interface MemberScrapRepository extends JpaRepository<MemberScrap, Long> {

    @Query("select count(ms) from MemberScrap ms where ms.member.id = :memberId and ms.scrapStudylog.id = :studylogId")
    int countByMemberIdAndScrapStudylogId(Long memberId, Long studylogId);
}
