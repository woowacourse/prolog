package wooteco.prolog.roadmap.application.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.roadmap.domain.Keyword;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KeywordsResponse {

    private List<KeywordResponse> data;

    public KeywordsResponse(final List<KeywordResponse> data) {
        this.data = data;
    }

    public static KeywordsResponse createResponse(final List<Keyword> keywords) {
        List<KeywordResponse> keywordsResponse = keywords.stream()
            .map(KeywordResponse::createResponse)
            .collect(Collectors.toList());
        return new KeywordsResponse(keywordsResponse);
    }
}
