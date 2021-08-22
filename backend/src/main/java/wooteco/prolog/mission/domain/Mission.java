package wooteco.prolog.mission.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.BaseEntity;
import wooteco.prolog.mission.exception.TooLongMissionNameException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Mission extends BaseEntity {

    public static final int MAX_LENGTH = 45;

    @Column(nullable = false, length = MAX_LENGTH)
    private String name;

    public Mission(String name) {
        this(null, name);
    }

    public Mission(Long id, String name) {
        super(id);
        validateMaxLength(name);
        this.name = name;
    }

    private void validateMaxLength(String name) {
        if (name.length() > MAX_LENGTH) {
            throw new TooLongMissionNameException();
        }
    }
}
