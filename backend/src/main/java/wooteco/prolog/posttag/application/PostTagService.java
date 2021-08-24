package wooteco.prolog.posttag.application;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.posttag.domain.PostTag;
import wooteco.prolog.posttag.domain.repository.PostTagRepository;
import wooteco.prolog.tag.domain.Tag;

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

    public List<PostTag> findByMember(Member member) {
        return postTagRepository.findByMember(member.getId());
    }
}
