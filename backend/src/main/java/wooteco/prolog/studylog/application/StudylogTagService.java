package wooteco.prolog.studylog.application;

import static java.util.stream.Collectors.toList;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.studylog.application.dto.TagResponse;
import wooteco.prolog.studylog.domain.StudylogTag;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.prolog.studylog.domain.repository.StudylogTagRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class StudylogTagService {

    private final StudylogTagRepository studylogTagRepository;

    public List<TagResponse> findTagsIncludedInStudylogs() {
        return studylogTagRepository.findTagsIncludedInStudylogs().stream()
            .map(TagResponse::of)
            .collect(toList());
    }

    public List<StudylogTag> findByTags(List<Tag> tags) {
        return studylogTagRepository.findByTagIn(tags);
    }
}
