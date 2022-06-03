package wooteco.prolog.studylog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StudylogWithScrapedCountResponse {

    private StudylogResponse studylogResponse;
    private int scrapedCount;
}
