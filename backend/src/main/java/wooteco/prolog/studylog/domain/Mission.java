package wooteco.prolog.studylog.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.studylog.exception.TooLongMissionNameException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Mission {

    public static final int MAX_LENGTH = 45;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = MAX_LENGTH)
    private String name;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    public Mission(String name, Level level) {
        this(null, name, level);
    }

    public Mission(Long id, String name, Level level) {
        this.id = id;
        validateMaxLength(name);
        this.name = name;
        this.level = level;
    }

    private void validateMaxLength(String name) {
        if (name.length() > MAX_LENGTH) {
            throw new TooLongMissionNameException();
        }
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mission)) {
            return false;
        }
        Mission mission = (Mission) o;
        return Objects.equals(id, mission.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
