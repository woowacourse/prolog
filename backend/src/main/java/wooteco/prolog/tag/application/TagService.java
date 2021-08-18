package wooteco.prolog.tag.application;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.tag.dao.PostTagDao;
import wooteco.prolog.tag.domain.Tag;
import wooteco.prolog.tag.domain.Tags;
import wooteco.prolog.tag.domain.repository.TagRepository;
import wooteco.prolog.tag.dto.TagRequest;
import wooteco.prolog.tag.dto.TagResponse;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;
    private final PostTagDao postTagDao;

    @Transactional
    public Tags create(List<TagRequest> tagRequests) {
        Tags requestTags = tagRequests.stream()
                .map(TagRequest::getName)
                .collect(collectingAndThen(toList(), Tags::of));

        Tags existTags = new Tags(tagRepository.findByNameValueIn(requestTags.toNames()));

        Tags newTags = requestTags.removeAllByName(existTags);
        tagRepository.saveAll(newTags.toList());

        return existTags.addAll(newTags);
    }

    public void addTagToPost(Long postId, List<Long> tagIds) {
        List<Long> originTags = postTagDao.findByPostId(postId);
        for (Long tagId : tagIds) {
            if (!originTags.contains(tagId)) {
                postTagDao.insert(postId, tagId);
            }
        }
    }

    public void removeTagFromPost(Long postId, List<Long> tagIds) {
        tagIds.forEach(tagId -> postTagDao.delete(postId, tagId));
    }

    public List<Tag> getTagsOfPost(Long id) {
        List<Long> tagIds = postTagDao.findByPostId(id);
        return tagIds.stream()
                .map(tagRepository::findById)
                .map(Optional::get)
                .collect(toList());
    }

    public List<TagResponse> findAllWithPost() {
        return postTagDao.findAll()
            .stream()
            .map(TagResponse::of)
            .collect(toList());
    }

    public List<TagResponse> findAll(){
        return tagRepository.findAll()
            .stream()
            .map(TagResponse::of)
            .collect(toList());
    }

    public void deletePostTagByPostId(Long postId) {
        postTagDao.deleteByPostId(postId);
    }
}
