package wooteco.prolog.tag.application;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.posttag.application.PostTagService;
import wooteco.prolog.posttag.domain.PostTag;
import wooteco.prolog.tag.domain.Tag;
import wooteco.prolog.tag.domain.Tags;
import wooteco.prolog.tag.domain.repository.TagRepository;
import wooteco.prolog.tag.dto.MemberTagResponse;
import wooteco.prolog.tag.dto.TagRequest;
import wooteco.prolog.tag.dto.TagResponse;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;
    private final PostTagService postTagService;
    private final MemberService memberService;

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

    public List<TagResponse> findTagsIncludedInPost() {
        return postTagService.findAll().stream()
            .map(PostTag::getTag)
            .distinct()
            .map(TagResponse::of)
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

    public List<MemberTagResponse> findByMember(String memberName) {
        final Member member = memberService.findByUsername(memberName);
        return MemberTagResponse.asListFrom(postTagService.findByMember(member));
    }
}
