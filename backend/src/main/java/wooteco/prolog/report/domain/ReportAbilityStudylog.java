package wooteco.prolog.report.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.NoArgsConstructor;
import wooteco.prolog.studylog.domain.Studylog;

@Entity
@NoArgsConstructor
public class ReportAbilityStudylog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "report_ability_id")
    private ReportAbility reportAbility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studylog_id", nullable = false)
    private Studylog studylog;

    private Long originAbilityId;

    public ReportAbilityStudylog(ReportAbility reportAbility, Studylog studylog, Long originAbilityId) {
        this.reportAbility = reportAbility;
        this.studylog = studylog;
        this.originAbilityId = originAbilityId;
    }
}
