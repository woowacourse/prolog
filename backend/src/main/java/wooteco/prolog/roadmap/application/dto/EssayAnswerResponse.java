package wooteco.prolog.roadmap.application.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.roadmap.domain.EssayAnswer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class EssayAnswerResponse {

    private Long id;
    private QuizResponse quiz;
    private String answer;
    private MemberResponse author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static EssayAnswerResponse of(EssayAnswer essayAnswer) {
        EssayAnswerResponse response = new EssayAnswerResponse();

        response.id = essayAnswer.getId();
        response.quiz = QuizResponse.of(essayAnswer.getQuiz());
        response.answer = essayAnswer.getAnswer();
        response.author = MemberResponse.of(essayAnswer.getMember());
        response.createdAt = essayAnswer.getCreatedAt();
        response.updatedAt = essayAnswer.getUpdatedAt();

        return response;
    }
}
