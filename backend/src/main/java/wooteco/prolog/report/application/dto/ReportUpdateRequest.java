package wooteco.prolog.report.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportUpdateRequest {

    private String title;
    private String description;
}
