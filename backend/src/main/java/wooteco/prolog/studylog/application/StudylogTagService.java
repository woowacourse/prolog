package wooteco.prolog.studylog.application;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.domain.StudylogTag;
import wooteco.prolog.studylog.domain.StudylogTags;
import wooteco.prolog.studylog.domain.repository.StudylogTagRepository;
import wooteco.prolog.studylog.domain.Tag;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class StudylogTagService {

    private final StudylogTagRepository studylogTagRepository;

    public List<StudylogTag> findAll() {
        return studylogTagRepository.findAll();
    }

    public List<StudylogTag> findByTags(List<Tag> tags) {
        return studylogTagRepository.findByTagIn(tags);
    }

    public StudylogTags findByMember(Member member) {
        final StudylogTags studylogTags = new StudylogTags();
        studylogTags.add(studylogTagRepository.findByMember(member.getId()));
        return studylogTags;
    }
}
