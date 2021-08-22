package wooteco.prolog.mission.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Mission extends BaseEntity {

    @Column
    private String name;

    public Mission(String name) {
        this(null, name);
    }

    public Mission(Long id, String name) {
        super(id);
        this.name = name;
    }
}
