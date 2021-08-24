package wooteco.prolog.tag.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
}
