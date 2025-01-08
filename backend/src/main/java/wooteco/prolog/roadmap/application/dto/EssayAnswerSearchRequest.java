package wooteco.prolog.roadmap.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
