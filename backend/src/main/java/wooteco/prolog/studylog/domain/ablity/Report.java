package wooteco.prolog.studylog.domain.ablity;

import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.domain.Studylog;

public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "report")
    List<ReportedAbility> reportedAbilities;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Studylog studylog;
}
