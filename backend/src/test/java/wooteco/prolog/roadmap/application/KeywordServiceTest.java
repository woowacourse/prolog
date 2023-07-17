package wooteco.prolog.roadmap.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static wooteco.prolog.common.exception.BadRequestCode.ROADMAP_KEYWORD_NOT_FOUND_EXCEPTION;

import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.roadmap.application.dto.KeywordCreateRequest;
import wooteco.prolog.roadmap.application.dto.KeywordUpdateRequest;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.repository.KeywordRepository;
import wooteco.prolog.session.domain.repository.SessionRepository;

@ExtendWith(MockitoExtension.class)
class KeywordServiceTest {

    @InjectMocks
    private KeywordService keywordService;

    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private KeywordRepository keywordRepository;

    @DisplayName("키워드를 생성할 때 세션이 존재하지 않으면 SessionNotFoundException가 발생한다")
    @Test
    void notExistSession() {
        //given
        when(sessionRepository.existsById(any())).thenReturn(false);

        //then
        assertThatThrownBy(() -> keywordService.createKeyword(1L, null));
    }

    @DisplayName("키워드를 생성할 때 부모 키워드가 주어졌지만 해당 부모가 존재하지 않으면 KeywordNotFoundException가 발생한다")
    @Test
    void notExistParentKeyword() {
        //given
        when(sessionRepository.existsById(any())).thenReturn(true);
        KeywordCreateRequest request = new KeywordCreateRequest(null, null, 1, 0, 1L);

        //then
        assertThatThrownBy(() -> keywordService.createKeyword(1L, request))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(ROADMAP_KEYWORD_NOT_FOUND_EXCEPTION.getMessage());
    }

    @DisplayName("세션 id로 키워드를 생성할 수 있다")
    @Test
    void createKeyword() {
        //given
        when(sessionRepository.existsById(any())).thenReturn(true);
        KeywordCreateRequest request = new KeywordCreateRequest(null, null, 1, 0, null);

        //when
        keywordService.createKeyword(1L, request);

        //then
        verify(keywordRepository, times(1)).save(any());
    }

    @DisplayName("keywordId에 해당하는 키워드가 없으면 KeywordNotFoundException가 발생한다")
    @Test
    void notExistKeyword() {
        //given
        when(sessionRepository.existsById(any())).thenReturn(true);

        //then
        assertThatThrownBy(() -> keywordService.findKeyword(1L, 1L))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(ROADMAP_KEYWORD_NOT_FOUND_EXCEPTION.getMessage());
    }

    @DisplayName("keywordId로 해당 키워드를 찾을 수 있다")
    @Test
    void findKeyword() {
        //given
        Keyword keyword = new Keyword(1L, null, null, 1, 1, null, null, null);
        when(keywordRepository.findById(any())).thenReturn(Optional.of(keyword));
        when(sessionRepository.existsById(any())).thenReturn(true);

        //when
        keywordService.findKeyword(1L, 1L);

        //then
        verify(keywordRepository, times(1)).findById(any());
    }

    @DisplayName("keywordId로 해당 키워드를 찾을 수 있다(자식들도 같이)")
    @Test
    void findKeywordWithAllChild() {
        //given
        when(sessionRepository.existsById(any())).thenReturn(true);
        when(keywordRepository.existsById(any())).thenReturn(true);

        Keyword keyword = new Keyword(1L, "", "", 1, 1, 1L, null, Collections.emptySet());
        when(keywordRepository.findFetchById(1L)).thenReturn(keyword);

        //when
        keywordService.findKeywordWithAllChild(1L, 1L);

        //then
        verify(keywordRepository, times(1)).findFetchById(any());
    }

    @DisplayName("sessionId로 최상위 키워드들을 찾을 수 있다")
    @Test
    void findSessionIncludeRootKeywords() {
        //given
        when(sessionRepository.existsById(any())).thenReturn(true);

        //when
        keywordService.findSessionIncludeRootKeywords(1L);

        //then
        verify(keywordRepository, times(1)).findBySessionIdAndParentIsNull(any());
    }

    @DisplayName("sessionId와 keywordId로 키워드를 업데이트할 수 있다")
    @Test
    void updateKeyword() {
        //given
        when(sessionRepository.existsById(any())).thenReturn(true);
        Keyword keyword = new Keyword(1L, "", "", 1, 1, 1L, null, Collections.emptySet());
        when(keywordRepository.findById(any())).thenReturn(Optional.of(keyword));

        KeywordUpdateRequest request = new KeywordUpdateRequest("", "", 1, 1, 1L);

        //when
        keywordService.updateKeyword(1L, 1L, request);

        //then
        verify(keywordRepository, times(2)).findById(any());
    }

    @DisplayName("sessionId가 유효하지 않으면 키워드 업데이트 시 예외가 발생한다")
    @Test
    void updateKeyword_fail() {
        //given
        when(sessionRepository.existsById(any())).thenReturn(false);
        KeywordUpdateRequest request = new KeywordUpdateRequest("", "", 1, 1, 1L);

        //then
        assertThatThrownBy(() -> keywordService.updateKeyword(1L, 1L, request));
    }

    @DisplayName("keywordId가 유효하지 않으면 키워드 업데이트 시 예외가 발생한다")
    @Test
    void updateKeyword_fail2() {
        //given
        when(sessionRepository.existsById(any())).thenReturn(true);
        KeywordUpdateRequest request = new KeywordUpdateRequest("", "", 1, 1, 1L);

        //then
        assertThatThrownBy(() -> keywordService.updateKeyword(1L, 1L, request))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(ROADMAP_KEYWORD_NOT_FOUND_EXCEPTION.getMessage());
    }

    @DisplayName("sessionId와 keywordId로 키워드를 삭제할 수 있다")
    @Test
    void deleteKeyword() {
        //given
        when(sessionRepository.existsById(any())).thenReturn(true);

        //when
        keywordService.deleteKeyword(1L, 1L);

        //then
        verify(keywordRepository, times(1)).findFetchById(any());
        verify(keywordRepository, times(1)).delete(any());
    }
}
