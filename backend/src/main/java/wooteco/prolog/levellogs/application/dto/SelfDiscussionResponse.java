package wooteco.prolog.levellogs.application.dto;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import wooteco.prolog.levellogs.domain.SelfDiscussion;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class SelfDiscussionResponse {

    private Long id;
    private String question;
    private String answer;

    public SelfDiscussionResponse(SelfDiscussion selfDiscussion) {
        this.id = selfDiscussion.getId();
        this.question = selfDiscussion.getQuestion();
        this.answer = selfDiscussion.getAnswer();
    }
}
