package wooteco.prolog.roadmap.application;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.roadmap.Keyword;
import wooteco.prolog.roadmap.application.dto.KeywordCreateRequest;
import wooteco.prolog.roadmap.application.dto.KeywordResponse;
import wooteco.prolog.roadmap.application.dto.KeywordUpdateRequest;
import wooteco.prolog.roadmap.exception.KeywordNotFoundException;
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
        keywordRepository.save(keyword);
        keyword.validateKeywordParent();

        return keyword.getId();
    }

    @Transactional(readOnly = true)
    public KeywordResponse findKeyword(final Long sessionId, final Long keywordId) {
        existSession(sessionId);
        Keyword keyword = keywordRepository.findById(keywordId)
            .orElseThrow(KeywordNotFoundException::new);

        return KeywordResponse.createResponse(keyword);
    }

    @Transactional(readOnly = true)
    public KeywordResponse findKeywordWithAllChild(final Long sessionId, final Long keywordId) {
        existSession(sessionId);
        existKeyword(keywordId);

        Keyword keyword = keywordRepository.findFetchById(keywordId);

        return KeywordResponse.createWithAllChildResponse(keyword);
    }

    public void updateKeyword(final Long sessionId, final Long keywordId, final KeywordUpdateRequest request) {
        existSession(sessionId);
        Keyword keyword = keywordRepository.findById(keywordId)
            .orElseThrow(KeywordNotFoundException::new);
        Keyword keywordParent = findKeywordParentOrNull(request.getParentKeywordId());

        keyword.update(
            request.getName(), request.getDescription(), request.getOrder(), request.getImportance(), keywordParent);
    }

    private void existSession(final Long sessionId) {
        boolean exists = sessionRepository.existsById(sessionId);
        if (!exists) {
            throw new SessionNotFoundException();
        }
    }

    private void existKeyword(final Long keywordId) {
        boolean exists = keywordRepository.existsById(keywordId);
        if (!exists) {
            throw new KeywordNotFoundException();
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
