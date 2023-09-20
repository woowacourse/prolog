package wooteco.prolog.admin.roadmap.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EssayAnswerSearchRequest {

    private Long curriculumId;
    private Long keywordId;
    private List<Long> quizIds;
    private List<Long> memberIds;
}
