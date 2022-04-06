package wooteco.prolog.ability.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.studylog.domain.Studylog;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@EqualsAndHashCode(of = "id")
public class StudylogAbility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ability_id", nullable = false)
    private Ability ability;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studylog_id", nullable = false)
    private Studylog studylog;

    public StudylogAbility(Long memberId, Ability ability, Studylog studylog) {
        this.memberId = memberId;
        this.ability = ability;
        this.studylog = studylog;
    }
}
