package wooteco.prolog.roadmap.application.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KeywordsResponse {

    private List<KeywordResponse> data;

    public KeywordsResponse(final List<KeywordResponse> data) {
        this.data = data;
    }
}
