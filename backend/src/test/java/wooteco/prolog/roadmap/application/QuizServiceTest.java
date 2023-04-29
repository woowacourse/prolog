package wooteco.prolog.roadmap.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.roadmap.application.dto.QuizRequest;
import wooteco.prolog.roadmap.application.dto.QuizResponse;
import wooteco.prolog.roadmap.application.dto.QuizzesResponse;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.Quiz;
import wooteco.prolog.roadmap.domain.repository.KeywordRepository;
import wooteco.prolog.roadmap.domain.repository.QuizRepository;
import wooteco.prolog.roadmap.exception.KeywordOrderException;
import wooteco.prolog.roadmap.exception.QuizNotFoundException;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {
    @Mock
    private KeywordRepository keywordRepository;

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    QuizService quizService;


    @Test
    @DisplayName("키워드 Id에 해당하는 Keyword 가 없으면 예외를 발생시킨다")
    void invalid_createQuiz_Keyword_Id() {
        //given
        when(keywordRepository.findById(anyLong()))
            .thenReturn(Optional.empty());

        //expect
        assertThatThrownBy(() -> quizService.createQuiz(1L, new QuizRequest("question")))
            .isInstanceOf(KeywordOrderException.class);
    }

    @Test
    @DisplayName("QuizRequest 를 보내면 해당 Request 의 Question 의 값과 Id 기반으로 찾은 Keyword 의 값을 가진 Quiz를 저장하고 그 Id를 반환한다")
    void valid_createQuiz() {
        //given
        final Keyword keyword = new Keyword(null, null, null, 2, 0, null, null, null);
        final String requestQuestion = "question";

        when(keywordRepository.findById(anyLong()))
            .thenReturn(Optional.of(keyword));
        when(quizRepository.save(any()))
            .thenReturn(new Quiz(keyword, null));

        final ArgumentCaptor<Quiz> quizArgumentCaptor = ArgumentCaptor.forClass(Quiz.class);

        //when
        quizService.createQuiz(1L, new QuizRequest(requestQuestion));
        verify(quizRepository, times(1)).save(quizArgumentCaptor.capture());

        final Quiz quiz = quizArgumentCaptor.getValue();

        //then
        assertAll(
            () -> assertThat(quiz.getKeyword()).isEqualTo(keyword),
            () -> assertThat(quiz.getQuestion()).isEqualTo(requestQuestion)
        );
    }

    @Test
    @DisplayName("keywordId에 매칭되는 키워드를 가진 모든 퀴즈를 통해 응답을 만들어준다")
    void findQuizzesByKeywordId() {
        //given
        final long requestKeywordId = 1L;

        when(quizRepository.findFetchQuizByKeywordId(anyLong()))
            .thenReturn(Arrays.asList(
                    new Quiz(null, null),
                    new Quiz(null, null)
                )
            );

        //when
        final QuizzesResponse quizzesByKeywordId = quizService.findQuizzesByKeywordId(requestKeywordId);

        //then
        assertAll(
            () -> assertThat(quizzesByKeywordId.getKeywordId()).isEqualTo(requestKeywordId),
            () -> assertThat(quizzesByKeywordId.getData()).hasSize(2)
        );
    }

    @Test
    @DisplayName("해당 quiz 아이디를 가진 quiz 가 없다면 예외를 발생시킨다")
    void invalid_updateQuiz() {
        //given
        when(quizRepository.findById(anyLong()))
            .thenReturn(Optional.empty());

        //expect
        assertThatThrownBy(() -> quizService.updateQuiz(1L, new QuizRequest()))
            .isInstanceOf(QuizNotFoundException.class);
    }

    @Test
    @DisplayName("찾은 quiz 의 question 을 업데이트 한다.")
    void valid_updateQuiz() {
        //given
        final String originQuestion = "origin";
        final Quiz targetQuiz = new Quiz(null, originQuestion);

        when(quizRepository.findById(anyLong()))
            .thenReturn(Optional.of(targetQuiz));

        //when
        final String updatedQuestion = "updated";
        quizService.updateQuiz(1L, new QuizRequest(updatedQuestion));

        //then
        assertThat(targetQuiz.getQuestion()).isEqualTo(updatedQuestion);
    }

    @Test
    @DisplayName("요청한 quizId 에 매핑되는 quiz 가 없으면 예외가 발생한다")
    void invalid_deleteQuiz() {
        //given
        when(quizRepository.existsById(anyLong()))
            .thenReturn(false);

        //expect
        assertThatThrownBy(() -> quizService.deleteQuiz(1L))
            .isInstanceOf(QuizNotFoundException.class);
    }

    @Test
    @DisplayName("요청한 quizId 에 매핑되는 quiz 를 저장소에서 삭제한다")
    void valid_deleteQuiz() {
        //given
        when(quizRepository.existsById(anyLong()))
            .thenReturn(true);

        //when
        quizService.deleteQuiz(1L);

        //then
        verify(quizRepository, times(1)).deleteById(anyLong());
    }

    @Test
    @DisplayName("퀴즈가 repository 에 존재하지 않는다면 예외가 발생한다")
    void invalid_findById() {
        //given
        when(quizRepository.findById(anyLong()))
            .thenReturn(Optional.empty());

        //expect
        assertThatThrownBy(() -> quizService.findById(1L))
            .isInstanceOf(QuizNotFoundException.class);
    }

    @Test
    @DisplayName("요청한 Id 기반으로 Quiz 로 만들어진 QuizResponse 를 반환해준다")
    void valid_findById() {
        //given
        final long findQuizId = 1L;
        final String findQuizQuestion = "question";
        when(quizRepository.findById(anyLong()))
            .thenReturn(Optional.of(new Quiz(findQuizId, null, findQuizQuestion)));

        //when
        final QuizResponse quizResponseById = quizService.findById(1L);

        //then
        assertAll(
            () -> assertThat(quizResponseById.getQuizId()).isEqualTo(findQuizId),
            () -> assertThat(quizResponseById.getQuestion()).isEqualTo(findQuizQuestion)
        );
    }

}
