package wooteco.prolog.studylog.application.dto.search;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

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
    private final Pageable pageable;

    public boolean hasOnlySearch() {
        return Objects.nonNull(keyword) && Objects.isNull(levels) &&
            Objects.isNull(missions) && Objects.isNull(tags) &&
            Objects.isNull(usernames);
    }
}
