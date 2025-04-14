package wooteco.prolog.interview.application;

import org.springframework.stereotype.Service;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.interview.domain.InterviewSession;
import wooteco.prolog.interview.domain.Interviewer;
import wooteco.prolog.interview.domain.repository.InterviewSessionRepository;
import wooteco.prolog.session.domain.repository.QuestionRepository;

import static wooteco.prolog.common.exception.BadRequestCode.INTERVIEW_SESSION_NOT_FOUND;
import static wooteco.prolog.common.exception.BadRequestCode.QUESTION_NOT_FOUND;

@Service
public class InterviewService {

    private final InterviewSessionRepository interviewSessionRepository;

    private final QuestionRepository questionRepository;

    private final Interviewer interviewer;

    private final InterviewSessionMapper interviewSessionMapper;

    InterviewService(
        final InterviewSessionRepository interviewSessionRepository,
        final QuestionRepository questionRepository,
        final Interviewer interviewer,
        final InterviewSessionMapper interviewSessionMapper
    ) {
        this.interviewSessionRepository = interviewSessionRepository;
        this.questionRepository = questionRepository;
        this.interviewer = interviewer;
        this.interviewSessionMapper = interviewSessionMapper;
    }

    public InterviewSessionResponse createSession(
        final Long memberId,
        final InterviewSessionRequest request
    ) {
        final var question = questionRepository.findById(request.questionId())
            .orElseThrow(() -> new BadRequestException(QUESTION_NOT_FOUND));

        final var interviewSession = new InterviewSession(memberId);
        interviewSession.start(interviewer, question);

        return interviewSessionMapper.mapToResponse(interviewSessionRepository.save(interviewSession));
    }

    public InterviewSessionResponse answer(
        final Long memberId,
        final Long sessionId,
        final InterviewAnswerRequest request
    ) {
        final var interviewSession = interviewSessionRepository.findById(sessionId)
            .orElseThrow(() -> new BadRequestException(INTERVIEW_SESSION_NOT_FOUND));

        interviewSession.answer(interviewer, memberId, request.answer());
        return interviewSessionMapper.mapToResponse(interviewSessionRepository.save(interviewSession));
    }
}
