package wooteco.prolog.report.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByNameValueIn(List<String> name);

    @Query("select t from Tag t where t.id in (select pt.tag.id from StudylogTag pt where pt.studylog = :studylog and pt.studylog.member = :member)")
    List<Tag> findByPostAndMember(Studylog studylog, Member member);
}
