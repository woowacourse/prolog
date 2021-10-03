package wooteco.prolog.studylog.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StudylogSearchCondition {

    private final List<String> keywords;
    private final List<Long> levels;
    private final List<Long> missions;
    private final List<Long> tags;
    private final List<Long> members;
    private final List<String> usernames;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public static StudylogSearchConditionBuilder builder() {
        return new StudylogSearchConditionBuilder();
    }

    public boolean hasOnlySearch() {
        return nullOrEmpty(levels) &&
            nullOrEmpty(missions) &&
            nullOrEmpty(tags) &&
            nullOrEmpty(usernames);
    }

    private boolean nullOrEmpty(List<?> list) {
        return Objects.isNull(list) || list.isEmpty();
    }
}
