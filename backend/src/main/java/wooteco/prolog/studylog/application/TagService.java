package wooteco.prolog.studylog.application;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.application.dto.TagRequest;
import wooteco.prolog.studylog.application.dto.TagResponse;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogTag;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.prolog.studylog.domain.Tags;
import wooteco.prolog.studylog.domain.repository.TagRepository;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;
    private final StudylogTagService studylogTagService;

    @Transactional
    public Tags findOrCreate(List<TagRequest> tagRequests) {
        Tags requestTags = tagRequests.stream()
            .map(TagRequest::getName)
            .collect(collectingAndThen(toList(), Tags::of));

        Tags existTags = new Tags(tagRepository.findByNameValueIn(requestTags.toNames()));

        Tags newTags = requestTags.removeAllByName(existTags);
        tagRepository.saveAll(newTags.getList());

        return existTags.addAll(newTags);
    }

    public List<TagResponse> findTagsIncludedInStudylogs() {
        return studylogTagService.findAll().stream()
            .map(StudylogTag::getTag)
            .distinct()
            .map(TagResponse::of)
            .sorted(Comparator.comparing(TagResponse::getName))
            .collect(toList());
    }

    public List<TagResponse> findAll() {
        return tagRepository.findAll()
            .stream()
            .map(TagResponse::of)
            .collect(toList());
    }

    public List<Tag> findByIds(List<Long> tagIds) {
        return tagRepository.findAllById(tagIds);
    }

    public Tags findByStudylogsAndMember(Studylog studylog, Member member) {
        return new Tags(tagRepository.findByStudylogAndMember(studylog, member));
    }
}
