package wooteco.prolog.studylog.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StudylogRequest {

    private String title;
    private String content;
    private Long missionId;
    private List<TagRequest> tags;
}
