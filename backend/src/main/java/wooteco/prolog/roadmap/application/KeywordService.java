package wooteco.prolog.roadmap.application;

import static wooteco.prolog.common.exception.BadRequestCode.ROADMAP_KEYWORD_NOT_FOUND_EXCEPTION;
import static wooteco.prolog.common.exception.BadRequestCode.ROADMAP_SESSION_NOT_FOUND_EXCEPTION;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.roadmap.application.dto.KeywordCreateRequest;
import wooteco.prolog.roadmap.application.dto.KeywordResponse;
import wooteco.prolog.roadmap.application.dto.KeywordUpdateRequest;
import wooteco.prolog.roadmap.application.dto.KeywordsResponse;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.repository.KeywordRepository;
import wooteco.prolog.session.domain.repository.SessionRepository;

@Transactional
@Service
public class KeywordService {

    private final SessionRepository sessionRepository;
    private final KeywordRepository keywordRepository;

    public KeywordService(final SessionRepository sessionRepository,
                          final KeywordRepository keywordRepository) {
        this.sessionRepository = sessionRepository;
        this.keywordRepository = keywordRepository;
    }

    /**
     * 최상위 키워드를 만드는 경우, 키워드의 부모 값에 null을 넣어줌
     */
    public Long createKeyword(final Long sessionId, final KeywordCreateRequest request) {
        existSession(sessionId);
        Keyword keywordParent = findKeywordParentOrNull(request.getParentKeywordId());

        Keyword keyword = request.toEntity(sessionId, keywordParent);
        keywordRepository.save(keyword);

        return keyword.getId();
    }

    @Transactional(readOnly = true)
    public KeywordResponse findKeyword(final Long keywordId) {
        Keyword keyword = keywordRepository.findById(keywordId)
            .orElseThrow(() -> new BadRequestException(ROADMAP_KEYWORD_NOT_FOUND_EXCEPTION));

        return KeywordResponse.createResponse(keyword);
    }

    @Transactional(readOnly = true)
    public KeywordResponse findKeywordWithAllChild(final Long sessionId, final Long keywordId) {
        existSession(sessionId);
        existKeyword(keywordId);

        Keyword keyword = keywordRepository.findFetchById(keywordId);

        return KeywordResponse.createWithAllChildResponse(keyword);
    }

    @Transactional(readOnly = true)
    public KeywordsResponse findRootKeywords() {
        List<Keyword> keywords = keywordRepository.findByParentIsNull();

        return KeywordsResponse.createResponse(keywords);
    }

    public void updateKeyword(final Long keywordId,
                              final KeywordUpdateRequest request) {
        Keyword keyword = keywordRepository.findById(keywordId)
            .orElseThrow(() -> new BadRequestException(ROADMAP_KEYWORD_NOT_FOUND_EXCEPTION));
        Keyword keywordParent = findKeywordParentOrNull(request.getParentKeywordId());

        keyword.update(request.getName(), request.getDescription(), request.getOrder(),
            request.getImportance(), keywordParent);
    }

    public void deleteKeyword(final Long keywordId) {
        Keyword keyword = keywordRepository.findFetchById(keywordId);

        keywordRepository.delete(keyword);
    }

    private void existSession(final Long sessionId) {
        boolean exists = sessionRepository.existsById(sessionId);
        if (!exists) {
            throw new BadRequestException(ROADMAP_SESSION_NOT_FOUND_EXCEPTION);
        }
    }

    private void existKeyword(final Long keywordId) {
        boolean exists = keywordRepository.existsById(keywordId);
        if (!exists) {
            throw new BadRequestException(ROADMAP_KEYWORD_NOT_FOUND_EXCEPTION);
        }
    }

    private Keyword findKeywordParentOrNull(final Long keywordId) {
        if (keywordId == null) {
            return null;
        }
        return keywordRepository.findById(keywordId)
            .orElseThrow(() -> new BadRequestException(ROADMAP_KEYWORD_NOT_FOUND_EXCEPTION));
    }
}
