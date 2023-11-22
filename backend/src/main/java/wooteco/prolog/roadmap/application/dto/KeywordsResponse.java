package wooteco.prolog.roadmap.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.roadmap.domain.Keyword;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KeywordsResponse {

    private List<KeywordResponse> data;

    public KeywordsResponse(final List<KeywordResponse> data) {
        this.data = data;
    }

    public static KeywordsResponse from(final List<Keyword> keywords) {
        final List<KeywordResponse> keywordsResponse = keywords.stream()
            .filter(Keyword::isRoot)
            .map(KeywordResponse::createWithAllChildResponse)
            .collect(Collectors.toList());

        return new KeywordsResponse(keywordsResponse);
    }

    public void setProgress(final Map<Long, Integer> totalQuizCounts, final Map<Long, Integer> answeredQuizCounts) {
        data.forEach(response -> response.setProgress(totalQuizCounts, answeredQuizCounts));
    }
}
