package wooteco.prolog.levellogs.application.dto;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import wooteco.prolog.levellogs.domain.SelfDiscussion;

@EqualsAndHashCode
@ToString
public class SelfDiscussionResponse {

    private final Long id;
    private final String question;
    private final String answer;

    public SelfDiscussionResponse(SelfDiscussion selfDiscussion) {
        this.id = selfDiscussion.getId();
        this.question = selfDiscussion.getQuestion();
        this.answer = selfDiscussion.getAnswer();
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
