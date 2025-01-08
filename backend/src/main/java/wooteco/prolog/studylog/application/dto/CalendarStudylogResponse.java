package wooteco.prolog.studylog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.studylog.domain.Studylog;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CalendarStudylogResponse {

    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CalendarStudylogResponse of(Studylog studylog) {
        return new CalendarStudylogResponse(studylog.getId(), studylog.getTitle(),
            studylog.getCreatedAt(), studylog
            .getUpdatedAt());
    }
}
