package wooteco.prolog.studylog.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.studylog.domain.StudylogTag;
import wooteco.prolog.studylog.domain.Tag;

public interface StudylogTagRepository extends JpaRepository<StudylogTag, Long> {

    List<StudylogTag> findByTagIn(List<Tag> tags);

    @Query("select pt from StudylogTag pt join fetch pt.tag where pt.studylog.member.id = :memberId")
    List<StudylogTag> findByMember(Long memberId);
}
