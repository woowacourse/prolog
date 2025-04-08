package wooteco.prolog.interview.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Embeddable
public class InterviewMessages {

    private static final int MAX_ROUND = 10;

    @ElementCollection
    @CollectionTable(
        name = "interview_message",
        joinColumns = @JoinColumn(name = "session_id")
    )
    @OrderColumn(name = "message_order")
    private List<InterviewMessage> values = new ArrayList<>();

    protected InterviewMessages() {
    }

    public InterviewMessages(final List<InterviewMessage> values) {
        this.values = new ArrayList<>(values);
    }

    public InterviewMessages with(final InterviewMessage message) {
        final var newValues = new ArrayList<>(values);
        newValues.add(message);
        return new InterviewMessages(newValues);
    }

    public int getRound() {
        return (int) values.stream()
            .filter(InterviewMessage::isByInterviewee)
            .count();
    }

    public List<InterviewMessage> values() {
        return Collections.unmodifiableList(values);
    }

    public boolean canFinish() {
        return getRound() >= MAX_ROUND && !hasInterviewerClosingSummary();
    }

    private boolean hasInterviewerClosingSummary() {
        return values.stream()
            .anyMatch(InterviewMessage::isInterviewerClosingSummary);
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public <R> Stream<R> map(final Function<? super InterviewMessage, ? extends R> mapper) {
        return values.stream()
            .map(mapper);
    }

    public Stream<InterviewMessage> filter(final Predicate<? super InterviewMessage> predicate) {
        return values.stream()
            .filter(predicate);
    }

    public InterviewMessage lastMessage() {
        return values.getLast();
    }

    @Override
    public String toString() {
        return "InterviewMessages{" +
            "values=" + values +
            '}';
    }
}
