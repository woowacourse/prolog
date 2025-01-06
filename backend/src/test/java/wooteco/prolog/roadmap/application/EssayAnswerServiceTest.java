package wooteco.prolog.roadmap.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.roadmap.application.dto.EssayAnswerRequest;
import wooteco.prolog.roadmap.application.dto.EssayAnswerUpdateRequest;
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
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static wooteco.prolog.common.exception.BadRequestCode.MEMBER_NOT_FOUND;

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

    @DisplayName("createEssayAnswer 를 실행할 때 MemberId 가 유효하지 않으면 예외가 발생한다")
    @Test
    void createEssayAnswer_fail_memberServiceFindById() {
        //given
        when(quizRepository.findById(anyLong())).thenReturn(
            Optional.of(new Quiz(null, "question")));
        when(memberService.findById(anyLong()))
            .thenThrow(new BadRequestException(MEMBER_NOT_FOUND));

        //expect
        assertThatThrownBy(() -> essayAnswerService.createEssayAnswer(ESSAY_ANSWER_REQUEST, 1L))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(MEMBER_NOT_FOUND.getMessage());
    }

    @DisplayName("createEssayAnswer 정상 요청을 보내면 생성된 essayAnswer 를 생성 하여 Id 를 반환해준다")
    @Test
    void createEssayAnswer() {
        //given
        when(quizRepository.findById(anyLong())).thenReturn(
            Optional.of(new Quiz(null, "question")));
        when(memberService.findById(anyLong())).thenReturn(
            new Member(null, null, Role.CREW, null, null));

        //when
        final Long essayAnswer = essayAnswerService.createEssayAnswer(ESSAY_ANSWER_REQUEST, 1L);

        //expect
        assertThat(essayAnswer).isNull();
    }

    @DisplayName("updateEssayAnswer 가 유효하다면 repository 에 저장한다")
    @Test
    void updateEssayAnswer() {
        //given
        when(essayAnswerRepository.findById(anyLong()))
            .thenReturn(
                Optional.of(new EssayAnswer(null, null, new Member(null, null, null, 1L, null))));
        when(memberService.findById((anyLong())))
            .thenReturn(new Member(null, null, null, 1L, null));

        //when
        essayAnswerService.updateEssayAnswer(1L, new EssayAnswerUpdateRequest("answer"), 1L);

        //then
        verify(essayAnswerRepository, times(1)).save(any());
    }

    @DisplayName("deleteEssayAnswer 에서 answerId memberId 에 매핑되는 EssayAnswer 가 없으면 예외가 발생한다")
    @Test
    void deleteEssayAnswer_fail() {
        //given
        when(essayAnswerRepository.findByIdAndMemberId(anyLong(), anyLong()))
            .thenReturn(Optional.empty());

        //when,then
        assertThatThrownBy(() -> essayAnswerService.deleteEssayAnswer(1L, 1L))
            .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("deleteEssayAnswer 에서 answerId memberId 에 매핑되는 EssayAnswer 가 있다면 삭제한다")
    @Test
    void deleteEssayAnswer() {
        //given
        when(essayAnswerRepository.findByIdAndMemberId(anyLong(), anyLong()))
            .thenReturn(Optional.of(new EssayAnswer(null, null, null)));

        //when
        essayAnswerService.deleteEssayAnswer(1L, 1L);

        //then
        verify(essayAnswerRepository, times(1)).deleteById(any());
    }

    @DisplayName("getById 에서 answerId에 해당하는EssayAnswer 가없으면 예외가 발생한다")
    @Test
    void getById_fail() {
        //given
        when(essayAnswerRepository.findById(anyLong()))
            .thenReturn(Optional.empty());

        //when,then
        assertThatThrownBy(() -> essayAnswerService.getById(1L))
            .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("getById 에서 answerId 에 해당하는 EssayAnswer 가있다면 반환해준다")
    @Test
    void getById() {
        //given
        final EssayAnswer expect = new EssayAnswer(null, null, null);
        when(essayAnswerRepository.findById(anyLong()))
            .thenReturn(Optional.of(expect));

        //when
        final EssayAnswer actual = essayAnswerService.getById(1L);

        //then
        assertThat(actual).isEqualTo(expect);
    }

    @DisplayName("findByQuizId는 EssayAnswer 의 quizId 값이 전달한 파라미터와 같은데이터를 모두 가져온다")
    @Test
    void findByQuizId() {
        //given
        when(essayAnswerRepository.findByQuizIdOrderByIdDesc(anyLong()))
            .thenReturn(Arrays.asList(new EssayAnswer(null, null, null),
                new EssayAnswer(null, null, null)));

        //when
        final List<EssayAnswer> byQuizId = essayAnswerService.findByQuizId(1L);

        //then
        assertThat(byQuizId).hasSize(2);
    }

}
