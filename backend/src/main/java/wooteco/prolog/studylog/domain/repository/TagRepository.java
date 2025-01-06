package wooteco.prolog.studylog.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.Tag;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByNameValueIn(List<String> name);

    @Query("select t from Tag t where t.id in (select pt.tag.id from StudylogTag pt where pt.studylog = :studylog and pt.studylog.member = :member)")
    List<Tag> findByStudylogAndMember(Studylog studylog, Member member);
}
