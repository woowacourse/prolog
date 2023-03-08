package wooteco.prolog.ability.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class StudylogPeriodRequest {

    //    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String startDate;
    //    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String endDate;
}
