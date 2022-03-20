package wooteco.prolog.report.domain;

import java.util.ArrayList;
import java.util.List;
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
import wooteco.prolog.member.domain.Member;

@Entity
@NoArgsConstructor
@Getter
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "report", fetch = FetchType.LAZY)
    private List<ReportAbility> abilities = new ArrayList<>();

    public Report(String title, String description, Member member) {
        this.title = title;
        this.description = description;
        this.member = member;
    }

    public boolean isBelongTo(Long memberId) {
        return member.getId().equals(memberId);
    }

    public void update(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
