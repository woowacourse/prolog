package wooteco.prolog.studylog.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.studylog.domain.PostTag;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.prolog.studylog.domain.repository.PostTagRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PostTagService {

    private final PostTagRepository postTagRepository;

    public List<PostTag> findAll() {
        return postTagRepository.findAll();
    }

    public List<PostTag> findByTags(List<Tag> tags) {
        return postTagRepository.findByTagIn(tags);
    }
}
