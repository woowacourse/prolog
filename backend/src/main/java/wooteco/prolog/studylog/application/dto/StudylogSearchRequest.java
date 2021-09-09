package wooteco.prolog.studylog.application.dto;

import java.time.LocalDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class StudylogSearchRequest {
    private Long tagId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
