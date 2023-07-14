package wooteco.prolog.roadmap.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EssayAnswerSearchRequest {

    private Long curriculumId;
    private Long keywordId;
    private List<Long> quizIds;
    private List<Long> memberIds;
}
