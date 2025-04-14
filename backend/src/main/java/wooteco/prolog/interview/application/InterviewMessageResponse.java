package wooteco.prolog.interview.application;

import wooteco.prolog.interview.domain.InterviewMessage;

import java.time.LocalDateTime;

public record InterviewMessageResponse(
    InterviewMessage.Sender sender,
    String content,
    String hint,
    LocalDateTime createdAt
) {

}
