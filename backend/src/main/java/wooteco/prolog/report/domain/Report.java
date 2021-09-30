package wooteco.prolog.report.domain;

import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.report.domain.abilitygraph.AbilityGraph;
import wooteco.prolog.report.domain.studylog.ReportedStudylog;
import wooteco.prolog.report.domain.studylog.ReportedStudylogs;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "graph_id", nullable = false)
    private AbilityGraph abilityGraph;

    private ReportedStudylogs studylogs;

    private Boolean isRepresent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public Report(String title,
                  String description,
                  AbilityGraph abilityGraph,
                  ReportedStudylogs studylogs,
                  Boolean isRepresent,
                  Member member
    ) {
     this(null, title, description, abilityGraph, studylogs, isRepresent, member);
    }

    public Report(Long id,
                  String title,
                  String description,
                  AbilityGraph abilityGraph,
                  ReportedStudylogs studylogs,
                  Boolean isRepresent,
                  Member member
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.abilityGraph = abilityGraph;
        this.studylogs = studylogs;
        this.isRepresent = isRepresent;
        this.member = member;

        abilityGraph.appendTo(this);
        studylogs.appendTo(this);
    }

    public void update(Report report) {
        this.title = report.title;
        this.description = report.description;
        this.abilityGraph.update(report.abilityGraph);
        this.studylogs.update(report.studylogs, null);
        this.isRepresent = report.isRepresent;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public AbilityGraph getAbilityGraph() {
        return abilityGraph;
    }

    public List<ReportedStudylog> getStudylogs() {
        return studylogs.getValues();
    }

    public Boolean isRepresent() {
        return isRepresent;
    }

    public Member getMember() {
        return member;
    }

    public void toUnRepresent() {
        this.isRepresent = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Report)) {
            return false;
        }
        Report report = (Report) o;
        return Objects.equals(getId(), report.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
