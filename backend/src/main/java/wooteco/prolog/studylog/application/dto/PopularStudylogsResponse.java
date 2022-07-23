package wooteco.prolog.studylog.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.studylog.domain.Studylog;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PopularStudylogsResponse {

//    private StudylogsWithScrapCountResponse allResponse;
//    private StudylogsWithScrapCountResponse frontResponse;
//    private StudylogsWithScrapCountResponse backResponse;
    private StudylogsResponse allResponse;
    private StudylogsResponse frontResponse;
    private StudylogsResponse backResponse;

    public static PopularStudylogsResponse of(List<Studylog> all, List<Studylog> backend, List<Studylog> frontend) {
        return new PopularStudylogsResponse(
                StudylogsResponse.of(all, 0, 0,0,null),
                StudylogsResponse.of(backend, 0, 0,0,null),
                StudylogsResponse.of(frontend, 0, 0,0,null));
    }
}
