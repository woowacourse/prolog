package wooteco.prolog.session.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import wooteco.prolog.session.domain.AnswerFeedback;
import wooteco.prolog.session.domain.AnswerUpdatedEvent;
import wooteco.prolog.session.domain.QnaFeedbackProvider;
import wooteco.prolog.session.domain.QnaFeedbackRequest;
import wooteco.prolog.session.domain.repository.AnswerFeedbackRepository;

@Service
public class AnswerFeedbackService {

    private static final Logger log = LoggerFactory.getLogger(AnswerFeedbackService.class);

    private final QnaFeedbackProvider qnaFeedbackProvider;
    private final AnswerFeedbackRepository answerFeedbackRepository;

    public AnswerFeedbackService(
        final QnaFeedbackProvider qnaFeedbackProvider,
        final AnswerFeedbackRepository answerFeedbackRepository
    ) {
        this.qnaFeedbackProvider = qnaFeedbackProvider;
        this.answerFeedbackRepository = answerFeedbackRepository;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleMemberUpdatedEvent(final AnswerUpdatedEvent event) {
        log.debug("AnswerUpdatedEvent: {}", event);

        final var answer = event.getAnswer();
        if (answer.getContent().isEmpty() || answer.getQuestion().getMission().getGoal().isEmpty()) {
            log.debug("Answer content or mission goal is empty: {}", answer);
            return;
        }

        final var feedbackRequest = new QnaFeedbackRequest(
            answer.getQuestion().getMission().getGoal(),
            answer.getQuestion().getContent(),
            answer.getContent()
        );
        final var feedbackContents = qnaFeedbackProvider.evaluate(feedbackRequest);
        final var answerFeedback = new AnswerFeedback(
            answer.getQuestion(),
            answer.getMemberId(),
            feedbackRequest,
            feedbackContents
        );

        answerFeedbackRepository.save(answerFeedback);
        log.debug("AnswerFeedback saved: {}", answerFeedback);
    }
}
