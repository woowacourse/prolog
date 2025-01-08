package wooteco.prolog.studylog.domain;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class StudylogDocument {

    private Long id;

    private String title;

    private String content;

    private List<Long> tagIds;

    private Long missionId;

    private Long levelId;

    private String username;

    private LocalDateTime dateTime;
}
