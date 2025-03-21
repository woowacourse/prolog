package wooteco.prolog.roadmap.application;

import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.roadmap.application.dto.KeywordsResponse;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.repository.KeywordRepository;
import wooteco.prolog.roadmap.domain.repository.dto.KeywordIdAndAnsweredQuizCount;
import wooteco.prolog.roadmap.domain.repository.dto.KeywordIdAndTotalQuizCount;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoadMapService {

    private final KeywordRepository keywordRepository;

    public KeywordsResponse findAllKeywordsWithProgress(final Long curriculumId, final Long memberId) {
        final List<Keyword> keywords = keywordRepository.findAllByCurriculumId(curriculumId);
        final Map<Long, Integer> totalQuizCounts = getTotalQuizCounts();
        final Map<Long, Integer> answeredQuizCounts = getAnsweredQuizCounts(memberId);

        return KeywordsResponse.of(keywords, totalQuizCounts, answeredQuizCounts);
    }

    private Map<Long, Integer> getTotalQuizCounts() {
        return keywordRepository.findTotalQuizCount().stream()
            .collect(
                toMap(
                    KeywordIdAndTotalQuizCount::getKeywordId,
                    KeywordIdAndTotalQuizCount::getTotalQuizCount));
    }

    private Map<Long, Integer> getAnsweredQuizCounts(final Long memberId) {
        return keywordRepository.findAnsweredQuizCountByMemberId(memberId).stream()
            .collect(
                toMap(
                    KeywordIdAndAnsweredQuizCount::getKeywordId,
                    KeywordIdAndAnsweredQuizCount::getAnsweredQuizCount));
    }
}
