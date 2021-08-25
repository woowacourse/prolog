package wooteco.prolog.tag.dto;

import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.posttag.domain.PostTags;
import wooteco.prolog.tag.domain.Tag;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberTagResponse {

    @JsonUnwrapped
    private TagResponse tagResponse;
    private int count;

    public static MemberTagResponse of(Tag tag, int count) {
        return new MemberTagResponse(TagResponse.of(tag), count);
    }

    public static List<MemberTagResponse> asListFrom(PostTags postTags) {
        return postTags.groupingWithcounting()
                .entrySet()
                .stream()
                .map(entry -> MemberTagResponse.of(entry.getKey(), Math.toIntExact(entry.getValue())))
                .collect(toList());
    }
}
