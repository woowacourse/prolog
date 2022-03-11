package wooteco.prolog.report.domain.report;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.report.domain.report.abilitygraph.AbilityGraph;
import wooteco.prolog.report.domain.report.studylog.ReportedStudylog;
import wooteco.prolog.report.domain.report.studylog.ReportedStudylogs;
import wooteco.prolog.report.exception.ReportDescriptionException;
import wooteco.prolog.report.exception.ReportTitleLengthException;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    private String description;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "graph_id", nullable = false)
    private AbilityGraph abilityGraph;

    private ReportedStudylogs studylogs;

    private Boolean isRepresent;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public Report2(String title,
                   String description,
                   AbilityGraph abilityGraph,
                   ReportedStudylogs studylogs,
                   Boolean isRepresent,
                   Member member
    ) {
     this(null, title, description, abilityGraph, studylogs, isRepresent, member);
    }

    public Report2(Long id,
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

        verifyTitleLength(title);
        verifyDescriptionLength(description);

        abilityGraph.appendTo(this);
        studylogs.appendTo(this);
    }

    private void verifyTitleLength(String title) {
        if(title.length() > 30) {
            throw new ReportTitleLengthException();
        }
    }

    private void verifyDescriptionLength(String description) {
        if(description.length() > 150) {
            throw new ReportDescriptionException();
        }
    }

    public void update(Report2 report) {
        this.title = report.title;
        this.description = report.description;
        this.abilityGraph.update(report.abilityGraph);
        this.studylogs.update(report.studylogs, this);
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
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
        if (!(o instanceof Report2)) {
            return false;
        }
        Report2 report = (Report2) o;
        return Objects.equals(getId(), report.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
