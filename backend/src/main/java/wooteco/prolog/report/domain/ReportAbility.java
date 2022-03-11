package wooteco.prolog.report.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ReportAbility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "report_id")
    private Report report;

    private Float weight;

    private Long originAbilityId;

    private String name;

    private String description;

    private String color;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ReportAbility parent;

    public ReportAbility(Report report, Float weight, Long originAbilityId, String name, String description, String color, ReportAbility parent) {
        this.report = report;
        this.weight = weight;
        this.originAbilityId = originAbilityId;
        this.name = name;
        this.description = description;
        this.color = color;
        this.parent = parent;
    }
}
