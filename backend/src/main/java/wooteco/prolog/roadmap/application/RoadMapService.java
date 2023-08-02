package wooteco.prolog.roadmap.application;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.roadmap.application.dto.KeywordsResponse;
import wooteco.prolog.roadmap.domain.Curriculum;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.repository.CurriculumRepository;
import wooteco.prolog.roadmap.domain.repository.KeywordRepository;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.SessionRepository;

@RequiredArgsConstructor
@Transactional
@Service
public class RoadMapService {

    private final CurriculumRepository curriculumRepository;
    private final SessionRepository sessionRepository;
    private final KeywordRepository keywordRepository;

    @Transactional(readOnly = true)
    public KeywordsResponse findAllKeywords(final Long curriculumId) {
        final Curriculum curriculum = curriculumRepository.findById(curriculumId)
            .orElseThrow(() -> new IllegalArgumentException(
                "해당 커리큘럼이 존재하지 않습니다. curriculumId = " + curriculumId));

        final Set<Long> sessionIds = sessionRepository.findAllByCurriculumId(curriculum.getId())
            .stream()
            .map(Session::getId)
            .collect(Collectors.toSet());

        final List<Keyword> keywords = keywordRepository.findBySessionIdIn(sessionIds);

        return KeywordsResponse.createResponseWithChildren(keywords);
    }
}
