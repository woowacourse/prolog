package wooteco.prolog.studylog.application.dto.search;

import java.util.List;
import java.util.Objects;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class StudyLogSearchParameters {

    private final String search;
    private final List<Long> levels;
    private final List<Long> missions;
    private final List<Long> tags;
    private final List<String> usernames;
    private final Pageable pageable;

    public StudyLogSearchParameters(String search, List<Long> levels,
                                    List<Long> missions, List<Long> tags,
                                    List<String> usernames,
                                    Pageable pageable) {
        this.search = search;
        this.levels = levels;
        this.missions = missions;
        this.tags = tags;
        this.usernames = usernames;
        this.pageable = pageable;
    }

    public boolean hasOnlySearch() {
        return Objects.nonNull(search) && Objects.isNull(levels) &&
            Objects.isNull(missions) && Objects.isNull(tags) &&
            Objects.isNull(usernames);
    }
}
