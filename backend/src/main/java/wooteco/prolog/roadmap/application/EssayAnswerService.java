package wooteco.prolog.roadmap.application;

import java.util.List;
import java.util.stream.Collectors;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.roadmap.application.dto.EssayAnswerRequest;
import wooteco.prolog.roadmap.application.dto.EssayAnswerSearchRequest;
import wooteco.prolog.roadmap.domain.Curriculum;
import wooteco.prolog.roadmap.domain.EssayAnswer;
import wooteco.prolog.roadmap.domain.Quiz;
import wooteco.prolog.roadmap.domain.repository.CurriculumRepository;
import wooteco.prolog.roadmap.domain.repository.EssayAnswerRepository;
import wooteco.prolog.roadmap.domain.repository.EssayAnswerSpecification;
import wooteco.prolog.roadmap.domain.repository.QuizRepository;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.prolog.studylog.application.dto.EssayAnswersResponse;

@Transactional
@Service
public class EssayAnswerService {

    private final EssayAnswerRepository essayAnswerRepository;
    private final QuizRepository quizRepository;
    private final MemberService memberService;
    private final CurriculumRepository curriculumRepository;
    private final SessionRepository sessionRepository;

    public EssayAnswerService(
        EssayAnswerRepository essayAnswerRepository,
        QuizRepository quizRepository,
        MemberService memberService,
        CurriculumRepository curriculumRepository,
        SessionRepository sessionRepository) {
        this.essayAnswerRepository = essayAnswerRepository;
        this.quizRepository = quizRepository;
        this.memberService = memberService;
        this.curriculumRepository = curriculumRepository;
        this.sessionRepository = sessionRepository;
    }

    @Transactional
    public Long createEssayAnswer(EssayAnswerRequest essayAnswerRequest, Long memberId) {
        Long quizId = essayAnswerRequest.getQuizId();
        Quiz quiz = quizRepository.findById(quizId)
            .orElseThrow(() -> new IllegalArgumentException("퀴즈가 존재하지 않습니다. quizId=" + quizId));

        Member member = memberService.findById(memberId);
        EssayAnswer essayAnswer = new EssayAnswer(quiz, essayAnswerRequest.getAnswer(), member);
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
        EssayAnswer essayAnswer = essayAnswerRepository.findById(answerId)
            .orElseThrow(() -> new IllegalArgumentException("답변이 존재하지 않습니다. answerId=" + answerId));
        Hibernate.initialize(essayAnswer.getQuiz());
        Hibernate.initialize(essayAnswer.getMember());

        return essayAnswer;
    }

    @Transactional(readOnly = true)
    public List<EssayAnswer> findByQuizId(Long quizId) {
        List<EssayAnswer> essayAnswers = essayAnswerRepository.findByQuizIdOrderByIdDesc(quizId);
        essayAnswers.forEach(it -> {
            Hibernate.initialize(it.getQuiz());
            Hibernate.initialize(it.getMember());
        });

        return essayAnswers;
    }

    public EssayAnswersResponse searchEssayAnswers(
        final EssayAnswerSearchRequest request,
        final Pageable pageable
    ) {

        final Long curriculumId = request.getCurriculumId();

        final Curriculum curriculum = curriculumRepository.findById(curriculumId)
            .orElseThrow(() -> new IllegalArgumentException(
                "해당 커리큘럼이 존재하지 않습니다. curriculumId = " + curriculumId));

        final List<Long> sessionIds = sessionRepository.findAllByCurriculumId(curriculum.getId())
            .stream()
            .map(Session::getId)
            .collect(Collectors.toList());

        if (sessionIds.isEmpty()) {
            throw new IllegalArgumentException("세션이 비어있으면 안됨");
        }

        final Specification<EssayAnswer> essayAnswerSpecification = EssayAnswerSpecification.equalsSessionIdsIn(
                sessionIds)
            .and(EssayAnswerSpecification.equalsKeywordId(request.getKeywordId()))
            .and(EssayAnswerSpecification.inQuizIds(request.getQuizIds()))
            .and(EssayAnswerSpecification.inMemberIds(request.getMemberIds()))
            .and(EssayAnswerSpecification.orderByIdDesc());

        final Page<EssayAnswer> essayAnswers = essayAnswerRepository.findAll(essayAnswerSpecification,
            pageable);
        return EssayAnswersResponse.of(essayAnswers);
    }
}
