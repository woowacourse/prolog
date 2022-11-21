package wooteco.prolog.roadmap.application.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.roadmap.domain.Quiz;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class QuizzesResponse {

    private Long keywordId;

    private List<QuizResponse> data;

    public QuizzesResponse(Long keywordId,
                           List<QuizResponse> data) {
        this.keywordId = keywordId;
        this.data = data;
    }

    public static QuizzesResponse of(Long keywordId, List<Quiz> quizzes) {
        final List<QuizResponse> responses = quizzes.stream().map(QuizResponse::of)
            .collect(Collectors.toList());
        return new QuizzesResponse(keywordId, responses);
    }
}
