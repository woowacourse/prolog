package wooteco.prolog.session.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.common.exception.BadRequestCode;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.studylog.domain.Curriculum;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Session {

    public static final int MAX_LENGTH = 45;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long curriculumId;

    @Column(nullable = false, length = MAX_LENGTH)
    private String name;

    public Session(String name) {
        this(null, null, name);
    }

    public Session(final Long curriculumId, final String name) {
        this(null, curriculumId, name);
    }

    public Session(final Long id, final Long curriculumId, final String name) {
        validateMaxLength(name);
        this.id = id;
        this.curriculumId = curriculumId;
        this.name = name;
    }

    public void update(final String name) {
        validateMaxLength(name);
        this.name = name;
    }

    private void validateMaxLength(String name) {
        if (name.length() > MAX_LENGTH) {
            throw new BadRequestException(BadRequestCode.TOO_LONG_LEVEL_NAME_EXCEPTION);
        }
    }

    public boolean isSameAs(Curriculum curriculum) {
        return curriculum.findCurriculum(this.name);
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Session level)) {
            return false;
        }
        return Objects.equals(id, level.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
