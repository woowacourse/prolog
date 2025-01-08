package wooteco.prolog.studylog.application.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class StudylogSearchRequest {

    private Long tagId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
