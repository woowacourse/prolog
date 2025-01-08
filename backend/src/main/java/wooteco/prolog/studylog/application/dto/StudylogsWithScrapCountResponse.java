package wooteco.prolog.studylog.application.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import wooteco.prolog.studylog.domain.Studylog;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StudylogsWithScrapCountResponse {

    private static final int ONE_INDEXED_PARAMETER = 1;

    private List<StudylogWithScrapedCountResponse> data;
    private Long totalSize;
    private int totalPage;
    private int currPage;

    public static StudylogsWithScrapCountResponse of(
        List<StudylogWithScrapedCountResponse> studylogWithScrapedCountResponses,
        Page<Studylog> page) {
        return new StudylogsWithScrapCountResponse(studylogWithScrapedCountResponses,
            page.getTotalElements(),
            page.getTotalPages(), page.getNumber() + ONE_INDEXED_PARAMETER);
    }

    public List<StudylogResponse> getStudylogResponses() {
        return data.stream()
            .map(StudylogWithScrapedCountResponse::getStudylogResponse)
            .collect(Collectors.toList());
    }
}
