package wooteco.prolog.studylog.domain;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.MemberTag;
import wooteco.prolog.studylog.exception.DuplicateTagException;

public class Tags {

    private final List<Tag> tags;

    public Tags(List<Tag> tags) {
        validateDuplicate(toNames(tags));
        this.tags = tags;
    }

    public static Tags of(List<String> tagNames) {
        List<Tag> tags = tagNames.stream()
            .map(Tag::new)
            .collect(toList());

        return new Tags(tags);
    }

    private void validateDuplicate(List<String> names) {
        Set<String> duplicateChecker = new HashSet<>(names);
        if (names.size() != duplicateChecker.size()) {
            throw new DuplicateTagException();
        }
    }

    public List<String> toNames() {
        return toNames(this.tags);
    }

    private List<String> toNames(List<Tag> tags) {
        return tags.stream()
            .map(Tag::getName)
            .collect(toList());
    }

    public Tags removeAllByName(Tags that) {
        return this.tags.stream()
            .filter(tag -> that.tags.stream().noneMatch(tag::isSameName))
            .collect(collectingAndThen(toList(), Tags::new));
    }

    public Tags addAll(Tags that) {
        this.tags.addAll(that.tags);
        return new Tags(this.tags);
    }

    public List<Tag> getList() {
        return this.tags;
    }

    public List<MemberTag> toMemberTags(Member member) {
        return tags.stream().map(tag -> new MemberTag(member, tag)).collect(toList());
    }
}
