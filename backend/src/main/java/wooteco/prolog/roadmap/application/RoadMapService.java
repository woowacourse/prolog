package wooteco.prolog.roadmap.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.roadmap.application.dto.KeywordsResponse;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.repository.KeywordRepository;
import wooteco.prolog.roadmap.domain.repository.dto.KeywordIdAndDoneQuizCount;
import wooteco.prolog.roadmap.domain.repository.dto.KeywordIdAndTotalQuizCount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoadMapService {

    private final KeywordRepository keywordRepository;

    public KeywordsResponse findAllKeywordsWithProgress(final Long curriculumId, final Long memberId) {
        final List<Keyword> keywords = keywordRepository.findAllByCurriculumId(curriculumId);
        final Map<Long, Integer> totalQuizCounts = getTotalQuizCounts();
        final Map<Long, Integer> doneQuizCounts = getDoneQuizCounts(memberId);

        final KeywordsResponse keywordsResponse = KeywordsResponse.of(keywords);
        keywordsResponse.setProgress(totalQuizCounts, doneQuizCounts);

        return keywordsResponse;
    }

    private Map<Long, Integer> getTotalQuizCounts() {
        final Map<Long, Integer> totalQuizCounts = new HashMap<>();

        for (KeywordIdAndTotalQuizCount totalQuizCount : keywordRepository.findTotalQuizCount()) {
            totalQuizCounts.put(totalQuizCount.getKeywordId(), totalQuizCount.getTotalQuizCount());
        }

        return totalQuizCounts;
    }

    private Map<Long, Integer> getDoneQuizCounts(final Long memberId) {
        final Map<Long, Integer> doneQuizCounts = new HashMap<>();
        if (isNull(memberId)) {
            return doneQuizCounts;
        }

        for (KeywordIdAndDoneQuizCount doneQuizCount : keywordRepository.findDoneQuizCountByMemberId(memberId)) {
            doneQuizCounts.put(doneQuizCount.getKeywordId(), doneQuizCount.getDoneQuizCount());
        }

        return doneQuizCounts;
    }
}
