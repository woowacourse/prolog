package wooteco.prolog.levellogs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelfDiscussion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "level_log_id")
    private LevelLog levelLog;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false, columnDefinition = "text")
    private String answer;

    public SelfDiscussion(final LevelLog levelLog, final String question, final String answer) {
        this.levelLog = levelLog;
        this.question = question;
        this.answer = answer;
    }
}
