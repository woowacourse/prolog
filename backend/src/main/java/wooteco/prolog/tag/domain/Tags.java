package wooteco.prolog.tag.domain;

import static java.util.stream.Collectors.collectingAndThen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import wooteco.prolog.tag.exception.DuplicateTagException;

public class Tags {

    private final List<Tag> tags;

    public Tags(List<Tag> tags) {
        validateDuplicate(toNames(tags));
        this.tags = tags;
    }

    public static Tags of(List<String> tagNames) {
        List<Tag> tags = tagNames.stream()
                .map(Tag::new)
                .collect(Collectors.toList());

        return new Tags(tags);
    }

    public List<String> toNames() {
        return toNames(this.tags);
    }

    public Tags removeAllById(Tags that) {
        List<Tag> thisTags = new ArrayList<>(this.tags);
        thisTags.removeAll(that.tags);
        return new Tags(thisTags);
    }

    public Tags removeAllByName(Tags that) {
        return this.tags.stream()
                .filter(tag -> that.tags.stream().noneMatch(tag::isSameName))
                .collect(collectingAndThen(Collectors.toList(), Tags::new));
    }

    public Tags addAll(Tags that) {
        this.tags.addAll(that.tags);
        return new Tags(this.tags);
    }

    public List<Tag> toList() {
        return this.tags;
    }

    private List<String> toNames(List<Tag> tags) {
        return tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
    }

    private void validateDuplicate(List<String> names) {
        Set<String> duplicateChecker = new HashSet<>(names);
        if (names.size() != duplicateChecker.size()) {
            throw new DuplicateTagException();
        }
    }

}
