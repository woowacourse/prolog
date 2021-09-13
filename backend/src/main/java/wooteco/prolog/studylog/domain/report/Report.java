package wooteco.prolog.studylog.domain.report;

import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.domain.report.abilitygraph.Graph;
import wooteco.prolog.studylog.domain.report.studylog.ReportedStudylog;
import wooteco.prolog.studylog.domain.report.studylog.ReportedStudylogs;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Embedded
    private Graph graph;

    private ReportedStudylogs studylogs;

    private Boolean isRepresent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public Report(String title,
                  String description,
                  Graph graph,
                  ReportedStudylogs studylogs,
                  Boolean isRepresent,
                  Member member
    ) {
     this(null, title, description, graph, studylogs, isRepresent, member);
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

    public Graph getGraph() {
        return graph;
    }

    public List<ReportedStudylog> getStudylogs() {
        return studylogs.getStudylogs();
    }

    public Boolean isRepresent() {
        return isRepresent;
    }

    public Member getMember() {
        return member;
    }
}
