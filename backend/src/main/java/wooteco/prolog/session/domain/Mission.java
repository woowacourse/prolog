package wooteco.prolog.session.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.common.exception.BadRequestException;

import static wooteco.prolog.common.exception.BadRequestCode.TOO_LONG_MISSION_NAME;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id")
public class Mission {

    public static final int MAX_LENGTH = 45;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = MAX_LENGTH)
    private String name;

    @Column(nullable = false)
    private String goal;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    public Mission(String name, Session session) {
        this(null, name, "", session);
    }

    // TODO: Add goal feature from view
    public Mission(String name, String goal, Session session) {
        this(null, name, goal, session);
    }

    public Mission(Long id, String name, String goal, Session session) {
        validateMaxLength(name);

        this.id = id;
        this.name = name;
        this.goal = goal;
        this.session = session;
    }

    private void validateMaxLength(String name) {
        if (name.length() > MAX_LENGTH) {
            throw new BadRequestException(TOO_LONG_MISSION_NAME);
        }
    }
}
