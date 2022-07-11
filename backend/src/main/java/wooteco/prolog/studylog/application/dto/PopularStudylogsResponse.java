package wooteco.prolog.studylog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PopularStudylogsResponse {

    private StudylogsWithScrapCountResponse allResponse;
    private StudylogsWithScrapCountResponse frontResponse;
    private StudylogsWithScrapCountResponse backResponse;
}
