package wooteco.prolog.admin.roadmap.application.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.roadmap.domain.EssayAnswer;
import wooteco.prolog.member.application.dto.MemberResponse;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class EssayAnswerResponse {

    private Long id;
    private EssayAnswerQuizResponse quiz;
    private String answer;
    private MemberResponse author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static EssayAnswerResponse of(EssayAnswer essayAnswer) {
        EssayAnswerResponse response = new EssayAnswerResponse();

        response.id = essayAnswer.getId();
        response.quiz = EssayAnswerQuizResponse.from(essayAnswer.getQuiz());
        response.answer = essayAnswer.getAnswer();
        response.author = MemberResponse.of(essayAnswer.getMember());
        response.createdAt = essayAnswer.getCreatedAt();
        response.updatedAt = essayAnswer.getUpdatedAt();

        return response;
    }
}
