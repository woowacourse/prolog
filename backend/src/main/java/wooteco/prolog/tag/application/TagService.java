package wooteco.prolog.tag.application;

import org.springframework.stereotype.Service;
import wooteco.prolog.tag.dao.PostTagDao;
import wooteco.prolog.tag.dao.TagDao;
import wooteco.prolog.tag.domain.Tag;
import wooteco.prolog.tag.dto.TagRequest;
import wooteco.prolog.tag.dto.TagResponse;
import wooteco.prolog.tag.exception.DuplicateTagException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagDao tagDao;
    private final PostTagDao postTagDao;

    public TagService(TagDao tagDao, PostTagDao postTagDao) {
        this.tagDao = tagDao;
        this.postTagDao = postTagDao;
    }

    public List<TagResponse> create(List<TagRequest> tagRequests) {
        List<String> requestedTagNames = tagRequests.stream()
                .map(TagRequest::getName)
                .collect(Collectors.toList());
        validate(requestedTagNames);

        List<Tag> tags = tagDao.findAll();

        List<Tag> updateTags = new ArrayList<>();
        for (String name : requestedTagNames) {
            Tag insertTag = tags.stream()
                    .filter(tag -> tag.sameAs(name))
                    .findAny()
                    .orElseGet(() -> tagDao.insert(name));
            updateTags.add(insertTag);
        }

        return updateTags.stream()
                .map(TagResponse::of)
                .collect(Collectors.toList());
    }

    private void validate(List<String> tagNames) {
        Set<String> stringSet = new HashSet<>(tagNames);
        if (tagNames.size() != stringSet.size()) {
            throw new DuplicateTagException();
        }
    }

    public void addTagToPost(Long postId, List<Long> tagIds) {
        tagIds.forEach(tagId -> postTagDao.insert(postId, tagId));
    }

    public List<TagResponse> getTagsOfPost(Long id) {
        List<Long> tagIds = postTagDao.findByPostId(id);
        return tagIds.stream()
                .map(tagDao::findById)
                .map(TagResponse::of)
                .collect(Collectors.toList());
    }

    public List<TagResponse> findAll() {
        return tagDao.findAll()
                .stream()
                .map(TagResponse::of)
                .collect(Collectors.toList());
    }
}
