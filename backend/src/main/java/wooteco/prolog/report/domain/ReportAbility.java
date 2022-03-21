package wooteco.prolog.report.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ReportAbility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    private Report report;

    private Integer weight;

    private Long originAbilityId;

    private String name;

    private String description;

    private String color;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "parent_id")
    private ReportAbility parent;

    @OneToMany(mappedBy = "reportAbility", fetch = FetchType.LAZY)
    private List<ReportAbilityStudylog> studylogs = new ArrayList<>();

    public ReportAbility(Report report, Integer weight, Long originAbilityId, String name, String description, String color, ReportAbility parent) {
        this.report = report;
        this.weight = weight;
        this.originAbilityId = originAbilityId;
        this.name = name;
        this.description = description;
        this.color = color;
        this.parent = parent;
    }
}
