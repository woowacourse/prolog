package wooteco.prolog.posttag.application;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.posttag.domain.PostTag;
import wooteco.prolog.posttag.domain.repository.PostTagRepository;
import wooteco.prolog.tag.domain.Tag;
import wooteco.prolog.tag.domain.Tags;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PostTagService {

    private final PostTagRepository postTagRepository;

    @Transactional
    public void addTagToPost(Post post, Tags tags) {
        List<PostTag> postTags = postTagRepository.findByPost(post);

        Tags existTags = toTags(postTags);

        Tags newTags = tags.removeAllById(existTags);

        newTags.toList().stream()
                .map(tag -> new PostTag(post, tag))
                .forEach(postTagRepository::save);
    }

    @Transactional
    public void updateTagToPost(Post post) {
        postTagRepository.saveAll(post.getPostTags());
    }

    @Transactional
    public void removeTagsOfPost(Post post) {
        postTagRepository.deleteByPost(post);
    }

    public List<PostTag> getPostTagsOfPost(Post post) {
        return postTagRepository.findByPost(post);
    }

    public List<PostTag> findAll() {
        return postTagRepository.findAll();
    }

    private Tags toTags(List<PostTag> postTags) {
        return postTags.stream()
                .map(PostTag::getTag)
                .collect(Collectors.collectingAndThen(toList(), Tags::new));
    }

    public List<PostTag> findByTags(List<Tag> tags) {
        return postTagRepository.findByTagIn(tags);
    }
}
