package wooteco.prolog.studylog.application.dto.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
public class StudylogsSearchRequest {

    private final String keyword;
    private final List<Long> sessions;
    private final List<Long> missions;
    private final List<Long> tags;
    private final List<String> usernames;
    private final List<Long> members;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final List<Long> ids;
    private final Pageable pageable;

}
