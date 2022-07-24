package wooteco.prolog.studylog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PopularStudylogsResponse {

    private StudylogsResponse allResponse;
    private StudylogsResponse frontResponse;
    private StudylogsResponse backResponse;

    public static PopularStudylogsResponse of(StudylogsResponse all,
                                              StudylogsResponse frontend,
                                              StudylogsResponse backend) {
        return new PopularStudylogsResponse(all, frontend, backend);
    }
}
