package wooteco.prolog.roadmap.application.dto;

import java.util.List;
import java.util.Map;
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

    public static KeywordsResponse of(final List<Keyword> keywords,
                                      final Map<Long, Integer> totalQuizCounts,
                                      final Map<Long, Integer> answeredQuizCounts) {
        final List<KeywordResponse> keywordsResponse = keywords.stream()
            .filter(Keyword::isRoot)
            .map(rootKeyword -> KeywordResponse.createWithAllChildResponse(rootKeyword, totalQuizCounts, answeredQuizCounts))
            .collect(Collectors.toList());

        return new KeywordsResponse(keywordsResponse);
    }
}
