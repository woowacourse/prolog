package wooteco.prolog.post.application.dto;

import java.time.LocalDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class PostSearchRequest {
    private Long tagId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
