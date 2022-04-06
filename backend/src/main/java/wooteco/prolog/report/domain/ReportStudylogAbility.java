package wooteco.prolog.report.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class ReportStudylogAbility {

    @Column(name = "report_studylog_ability_name")
    private String name;
    @Column(name = "report_studylog_ability_color")
    private String color;

    public ReportStudylogAbility(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
