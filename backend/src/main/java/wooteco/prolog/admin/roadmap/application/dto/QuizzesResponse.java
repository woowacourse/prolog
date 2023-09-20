package wooteco.prolog.admin.roadmap.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
