package wooteco.prolog.studylog.application.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.member.domain.MemberTag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberTagResponse {

    @JsonUnwrapped
    private TagResponse tagResponse;
    private int count;

    public static MemberTagResponse of(MemberTag memberTag) {
        return new MemberTagResponse(TagResponse.of(memberTag.getTag()), memberTag.getCount());
    }

    public static List<MemberTagResponse> asListFrom(List<MemberTag> memberTags, int stuylogCount) {
        List<MemberTagResponse> memberTagResponses = new ArrayList<>();
        final TagResponse allTagResponse = new TagResponse(0L, "ALL");
        memberTagResponses.add(new MemberTagResponse(allTagResponse, stuylogCount));
        memberTagResponses.addAll(memberTags.stream()
            .map(MemberTagResponse::of)
            .collect(Collectors.toList()));
        return memberTagResponses;
    }
}
