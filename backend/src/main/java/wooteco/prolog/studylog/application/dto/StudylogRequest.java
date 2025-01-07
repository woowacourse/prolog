package wooteco.prolog.studylog.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.compress.utils.Lists;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StudylogRequest {

    private String title;
    private String content;
    private Long sessionId;
    private Long missionId;
    private List<TagRequest> tags = Lists.newArrayList();
    private List<AnswerRequest> answers = Lists.newArrayList();

    public StudylogRequest(String title, String content, Long missionId, List<TagRequest> tags) {
        this.title = title;
        this.content = content;
        this.missionId = missionId;
        this.tags = tags;
    }
}
