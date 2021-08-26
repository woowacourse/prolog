package wooteco.prolog.studylog.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.studylog.application.dto.TagRequest;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.prolog.studylog.domain.Tags;
import wooteco.prolog.studylog.domain.repository.TagRepository;

import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;
    private final PostTagService postTagService;

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

    public List<wooteco.prolog.studylog.application.dto.TagResponse> findAll() {
        return tagRepository.findAll()
                .stream()
                .map(wooteco.prolog.studylog.application.dto.TagResponse::of)
                .collect(toList());
    }

    public List<Tag> findByIds(List<Long> tagIds) {
        return tagRepository.findAllById(tagIds);
    }
}
