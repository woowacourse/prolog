package wooteco.prolog.levellogs.application.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class SelfDiscussionRequest {

    private String question;
    private String answer;

    public SelfDiscussionRequest(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
}
