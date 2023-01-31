package wooteco.prolog.roadmap.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.roadmap.domain.EssayAnswer;
import wooteco.prolog.roadmap.domain.Quiz;
import wooteco.prolog.roadmap.domain.repository.EssayAnswerRepository;
import wooteco.prolog.roadmap.domain.repository.QuizRepository;

@Transactional
@Service
public class EssayAnswerService {

    private final EssayAnswerRepository essayAnswerRepository;
    private final QuizRepository quizRepository;
    private final MemberService memberService;

    @Autowired
    public EssayAnswerService(EssayAnswerRepository essayAnswerRepository,
                              QuizRepository quizRepository,
                              MemberService memberService) {
        this.essayAnswerRepository = essayAnswerRepository;
        this.quizRepository = quizRepository;
        this.memberService = memberService;
    }

    @Transactional
    public Long createEssayAnswer(Long quizId, String answer, Long memberId) {
        Quiz quiz = quizRepository.findById(quizId)
            .orElseThrow(() -> new IllegalArgumentException("퀴즈가 존재하지 않습니다. quizId=" + quizId));
        Member member = memberService.findById(memberId);

        EssayAnswer essayAnswer = new EssayAnswer(quiz, answer, member);
        essayAnswerRepository.save(essayAnswer);

        return essayAnswer.getId();
    }

    @Transactional
    public void updateEssayAnswer(Long answerId, String answer, Long memberId) {
        EssayAnswer essayAnswer = getById(answerId);
        Member member = memberService.findById(memberId);

        essayAnswer.update(answer, member);
        essayAnswerRepository.save(essayAnswer);
    }

    @Transactional
    public void deleteEssayAnswer(Long answerId, Long memberId) {
        if (!essayAnswerRepository.findByIdAndMemberId(answerId, memberId).isPresent()) {
            throw new IllegalArgumentException("답변이 존재하지 않습니다. answerId=" + answerId);
        }
        essayAnswerRepository.deleteById(answerId);
    }

    @Transactional(readOnly = true)
    public EssayAnswer getById(Long answerId) {
        return essayAnswerRepository.findById(answerId)
            .orElseThrow(() -> new IllegalArgumentException("답변이 존재하지 않습니다. answerId=" + answerId));
    }
}
