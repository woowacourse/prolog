package wooteco.prolog.report.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ReportAbility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String color;
    private Integer weight;
    private Long originAbilityId;
    private Long reportId;

    public ReportAbility(String name, String description, String color, Integer weight,
                         Long originAbilityId, Long reportId) {
        this.originAbilityId = originAbilityId;
        this.weight = weight;
        this.name = name;
        this.description = description;
        this.color = color;
        this.reportId = reportId;
    }
}
