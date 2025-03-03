package wooteco.prolog.session.ui;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.session.domain.AnswerUpdatedEvent;
import wooteco.prolog.session.domain.repository.AnswerRepository;

import java.util.stream.IntStream;

@RestController
@RequestMapping("/feedbacks/ASDLKJWOIDSADASDSFDSLKJ")
@AllArgsConstructor
public class TempFeedbackController {

    private final AnswerRepository answerRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    @GetMapping
    public void show(
        @RequestParam("startId") Long startId,
        @RequestParam("endId") Long endId
    ) {
        final var ids = IntStream.rangeClosed(startId.intValue(), endId.intValue())
            .mapToLong(Long::valueOf)
            .boxed()
            .toList();
        final var answers = answerRepository.findAllById(ids);

        answers.forEach(answer -> {
            applicationEventPublisher.publishEvent(new AnswerUpdatedEvent(answer));
        });
    }
}
