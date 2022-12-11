package wooteco.prolog.roadmap.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.roadmap.exception.CurriculumInvalidException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Curriculum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public Curriculum(Long id, String name) {
        this.id = id;
        this.name = name;
        validateName(name);
    }

    public Curriculum(String name) {
        this(null, name);
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new CurriculumInvalidException();
        }
    }

    public void updateName(String name) {
        validateName(name);
        this.name = name;
    }
}
