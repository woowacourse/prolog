package wooteco.prolog.ability.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import wooteco.prolog.studylog.domain.StudylogTemp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StudylogTempAbility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ability_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Ability ability;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studylog_temp_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private StudylogTemp studylogTemp;

    public StudylogTempAbility(Long memberId, Ability ability, StudylogTemp studylogTemp) {
        this.memberId = memberId;
        this.ability = ability;
        this.studylogTemp = studylogTemp;
    }
}
