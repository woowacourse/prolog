package wooteco.prolog.roadmap.application;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.roadmap.Keyword;
import wooteco.prolog.roadmap.application.dto.KeywordCreateRequest;
import wooteco.prolog.roadmap.repository.KeywordRepository;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.prolog.session.exception.SessionNotFoundException;

@Transactional
@Service
public class KeywordService {

    private final SessionRepository sessionRepository;
    private final KeywordRepository keywordRepository;

    public KeywordService(final SessionRepository sessionRepository, final KeywordRepository keywordRepository) {
        this.sessionRepository = sessionRepository;
        this.keywordRepository = keywordRepository;
    }

    public Long createKeyword(final Long sessionId, final KeywordCreateRequest request) {
        existSession(sessionId);
        Keyword keywordParent = findKeywordParentOrNull(request.getParentKeywordId());
        Keyword keyword = createKeyword(sessionId, request, keywordParent);

        return keywordRepository.save(keyword).getId();
    }

    private void existSession(final Long sessionId) {
        boolean exists = sessionRepository.existsById(sessionId);
        if (!exists) {
            throw new SessionNotFoundException();
        }
    }

    private Keyword findKeywordParentOrNull(final Long keywordId) {
        if (keywordId == null) {
            return null;
        }
        Optional<Keyword> findKeyword = keywordRepository.findById(keywordId);
        return findKeyword.orElse(null);
    }

    private Keyword createKeyword(final Long sessionId, final KeywordCreateRequest request, final Keyword keywordParent) {
        return Keyword.createKeyword(
            request.getName(), request.getDescription(), request.getOrder(),
            request.getImportance(), sessionId, keywordParent
        );
    }
}
