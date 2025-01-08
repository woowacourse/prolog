package wooteco.prolog.levellogs.application.dto;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelfDiscussionRequest {

    @NotNull
    private String question;
    @NotNull
    private String answer;

    public SelfDiscussionRequest(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
}
