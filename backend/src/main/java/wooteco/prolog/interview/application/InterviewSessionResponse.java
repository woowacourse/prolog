package wooteco.prolog.interview.application;

import java.util.List;

public record InterviewSessionResponse(
    Long id,
    Long memberId,
    boolean finished,
    List<InterviewMessageResponse> messages
) {

}
