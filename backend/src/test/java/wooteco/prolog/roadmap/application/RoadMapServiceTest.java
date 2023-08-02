package wooteco.prolog.roadmap.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.roadmap.application.dto.KeywordsResponse;
import wooteco.prolog.roadmap.domain.Curriculum;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.repository.CurriculumRepository;
import wooteco.prolog.roadmap.domain.repository.KeywordRepository;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.SessionRepository;

@ExtendWith(MockitoExtension.class)
class RoadMapServiceTest {

    @Mock
    private CurriculumRepository curriculumRepository;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private KeywordRepository keywordRepository;
    @InjectMocks
    private RoadMapService roadMapService;

    @Test
    @DisplayName("curriculumId가 주어지면 해당 커리큘럼의 키워드들을 전부 조회할 수 있다.")
    void findAllKeywords() throws Exception {
        //given
        final Curriculum curriculum = new Curriculum(1L, "커리큘럼1");
        final Session session = new Session(1L, curriculum.getId(), "세션1");
        final List<Session> sessions = Arrays.asList(session);
        final Keyword keyword = Keyword.createKeyword("자바1", "자바 설명1", 1, 5, session.getId(),
            null);

        when(curriculumRepository.findById(anyLong()))
            .thenReturn(Optional.of(curriculum));

        when(sessionRepository.findAllByCurriculumId(anyLong()))
            .thenReturn(sessions);

        when(keywordRepository.findBySessionIdIn(any()))
            .thenReturn(Arrays.asList(keyword));

        final KeywordsResponse expected = KeywordsResponse.createResponse(Arrays.asList(keyword));

        //when
        final KeywordsResponse actual =
            roadMapService.findAllKeywords(curriculum.getId());

        //then
        assertThat(actual)
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }
}
