package wooteco.prolog.tag.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
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
        List<MemberTagResponse> memberTagResponses = new ArrayList<>();
        int allCount = 0;
        for (Entry<Tag, Long> postTagEntry : postTags.groupingWithCounting().entrySet()) {
            final int count = Math.toIntExact(postTagEntry.getValue());
            allCount += count;
            memberTagResponses.add(MemberTagResponse.of(postTagEntry.getKey(), count));
        }
        final TagResponse allTagResponse = new TagResponse(0L, "all");
        memberTagResponses.add(new MemberTagResponse(allTagResponse, allCount));
        return memberTagResponses;
    }
}
