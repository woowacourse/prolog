package wooteco.prolog.studylog.application.dto.search;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import wooteco.prolog.studylog.domain.repository.StudylogSearchCondition;

@AllArgsConstructor
@Getter
public class StudylogsSearchRequest {

    private final String keyword;
    private final List<Long> levels;
    private final List<Long> missions;
    private final List<Long> tags;
    private final List<String> usernames;
    private final List<Long> members;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final List<Long> ids;
    private final Pageable pageable;

    public StudylogSearchCondition asSearchConditionWithoutKeyword() {
        return StudylogSearchCondition.builder()
            .levels(levels)
            .missions(missions)
            .tags(tags)
            .usernames(usernames)
            .members(members)
            .endDate(endDate)
            .startDate(startDate)
            .build();
    }
}
