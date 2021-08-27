package wooteco.prolog.level.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.level.exception.TooLongLevelNameException;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Level {

    public static final int MAX_LENGTH = 45;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = MAX_LENGTH)
    private String name;

    public Level(String name) {
        this(null, name);
    }

    public Level(Long id, String name) {
        this.id = id;
        validateMaxLength(name);
        this.name = name;
    }

    private void validateMaxLength(String name) {
        if (name.length() > MAX_LENGTH) {
            throw new TooLongLevelNameException();
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
        if (!(o instanceof Level)) {
            return false;
        }
        Level level = (Level) o;
        return Objects.equals(id, level.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
