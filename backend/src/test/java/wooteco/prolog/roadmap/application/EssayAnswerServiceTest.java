package wooteco.prolog.roadmap.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.exception.MemberNotFoundException;
import wooteco.prolog.roadmap.application.dto.EssayAnswerRequest;
import wooteco.prolog.roadmap.domain.EssayAnswer;
import wooteco.prolog.roadmap.domain.Quiz;
import wooteco.prolog.roadmap.domain.repository.EssayAnswerRepository;
import wooteco.prolog.roadmap.domain.repository.QuizRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EssayAnswerServiceTest {
    private static final EssayAnswerRequest ESSAY_ANSWER_REQUEST = new EssayAnswerRequest(1L, null);

    @Mock
    private EssayAnswerRepository essayAnswerRepository;
    @Mock
    private QuizRepository quizRepository;
    @Mock
    private MemberService memberService;

    @InjectMocks
    private EssayAnswerService essayAnswerService;

    @Test
    void createEssayAnswer_를_실행할_때_Quiz_Id_가_유효하지_않으면_예외가_발생한다() {
        //given
        when(quizRepository.findById(anyLong())).thenReturn(Optional.empty());

        //expect
        assertThatThrownBy(() -> essayAnswerService.createEssayAnswer(ESSAY_ANSWER_REQUEST, null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createEssayAnswer_를_실행할_때_Member_Id_가_유효하지_않으면_예외가_발생한다() {
        //given
        when(quizRepository.findById(anyLong())).thenReturn(Optional.of(new Quiz(null, "question")));
        when(memberService.findById(anyLong())).thenThrow(MemberNotFoundException.class);

        //expect
        assertThatThrownBy(() -> essayAnswerService.createEssayAnswer(ESSAY_ANSWER_REQUEST, 1L))
            .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    void createEssayAnswer_정상_요청을_보내면_생성된_essayAnswer_를_생성_하여_Id_를_반환해준다() {
        //given
        when(quizRepository.findById(anyLong())).thenReturn(Optional.of(new Quiz(null, "question")));
        when(memberService.findById(anyLong())).thenReturn(new Member(null, null, null, null, null));

        //when
        final Long essayAnswer = essayAnswerService.createEssayAnswer(ESSAY_ANSWER_REQUEST, 1L);

        //expect
        assertThat(essayAnswer).isNull();
    }

    @Test
    void updateEssayAnswer_의_파라미터로_넘긴_answerId_로_인해_getById_에서_예외가_발생할_수_있다() {
        //given
        when(essayAnswerRepository.findById(anyLong())).thenReturn(Optional.empty());

        //expect
        assertThatThrownBy(() -> essayAnswerService.updateEssayAnswer(1L, "a", 1L)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void updateEssayAnswer_의_파라미터로_넘긴_memberId_가_유효하지_않으면_예외가_발생한다() {
        //given
        when(essayAnswerRepository.findById(anyLong()))
            .thenReturn(Optional.of(new EssayAnswer(null, null, null)));
        when(memberService.findById((anyLong())))
            .thenThrow(MemberNotFoundException.class);

        //expect
        assertThatThrownBy(() -> essayAnswerService.updateEssayAnswer(1L, null, 1L))
            .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    void updateEssayAnswer_가_유효하다면_repository_에_저장한다() {
        //given
        when(essayAnswerRepository.findById(anyLong()))
            .thenReturn(Optional.of(new EssayAnswer(null, null, new Member(null, null, null, 1L, null))));
        when(memberService.findById((anyLong())))
            .thenReturn(new Member(null, null, null, 1L, null));

        //when
        essayAnswerService.updateEssayAnswer(1L, "answer", 1L);

        //then
        verify(essayAnswerRepository, times(1)).save(any());
    }

    @Test
    void deleteEssayAnswer_에서_answerId_memberId_에_매핑되는_EssayAnswer_가_없으면_예외가_발생한다() {
        //given
        when(essayAnswerRepository.findByIdAndMemberId(anyLong(), anyLong()))
            .thenReturn(Optional.empty());

        //expect
        assertThatThrownBy(() -> essayAnswerService.deleteEssayAnswer(1L, 1L))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void deleteEssayAnswer_에서_answerId_memberId_에_매핑되는_EssayAnswer_가_있다면_삭제한다() {
        //given
        when(essayAnswerRepository.findByIdAndMemberId(anyLong(), anyLong()))
            .thenReturn(Optional.of(new EssayAnswer(null, null, null)));

        //when
        essayAnswerService.deleteEssayAnswer(1L, 1L);

        //then
        verify(essayAnswerRepository, times(1)).deleteById(any());
    }

    @Test
    void getById_에서_answerId_에_해당하는_EssayAnswer_가_없으면_예외가_발생한다() {
        //given
        when(essayAnswerRepository.findById(anyLong()))
            .thenReturn(Optional.empty());

        //expect
        assertThatThrownBy(() -> essayAnswerService.getById(1L))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getById_에서_answerId_에_해당하는_EssayAnswer_가_있다면_반환해준다() {
        //given
        final EssayAnswer expect = new EssayAnswer(null, null, null);
        when(essayAnswerRepository.findById(anyLong()))
            .thenReturn(Optional.of(expect));

        //when
        final EssayAnswer actual = essayAnswerService.getById(1L);

        //then
        assertThat(expect).isEqualTo(actual);
    }

    @Test
    void findByQuizId_는_EssayAnswer_의_quizId_값이_전달한_파라미터와_같은_데이터를_모두_가져온다() {
        //given
        when(essayAnswerRepository.findByQuizIdOrderByIdDesc(anyLong()))
            .thenReturn(Arrays.asList(new EssayAnswer(null, null, null), new EssayAnswer(null, null, null)));

        //when
        final List<EssayAnswer> byQuizId = essayAnswerService.findByQuizId(1L);

        for (EssayAnswer essayAnswer : byQuizId) {
            System.out.println("hello");
        }

        //then
        assertThat(byQuizId).hasSize(2);
    }

}
