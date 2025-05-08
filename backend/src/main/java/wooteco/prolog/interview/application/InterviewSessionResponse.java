package wooteco.prolog.interview.application;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record InterviewSessionResponse(
    Long id,
    Long memberId,
    boolean finished,
    List<InterviewMessageResponse> messages,
    int round
) {
    private static final int MAX_ROUND = 10;

    @JsonProperty("remainRound")
    public int remainRound() {
        return MAX_ROUND - round;
    }
}
