package wooteco.prolog.session.domain;

import org.springframework.context.ApplicationEvent;

public class AnswerUpdatedEvent extends ApplicationEvent {

    public AnswerUpdatedEvent(final Answer answer) {
        super(answer);
    }

    public Answer getAnswer() {
        return (Answer) getSource();
    }
}
