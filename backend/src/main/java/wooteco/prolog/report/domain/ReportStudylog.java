package wooteco.prolog.report.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.studylog.domain.Studylog;

@Entity
@NoArgsConstructor
@Getter
public class ReportStudylog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reportId;

    @ManyToOne
    @JoinColumn(name = "report_ability_id")
    private ReportAbility reportAbility;

    @ManyToOne
    @JoinColumn(name = "studylog_id")
    private Studylog studylog;

    public ReportStudylog(Long reportId, ReportAbility reportAbility, Studylog studylog) {
        this.reportId = reportId;
        this.reportAbility = reportAbility;
        this.studylog = studylog;
    }
}
