package wooteco.prolog.member.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberGroup {

    private static final String BACKEND = "백엔드";
    private static final String FRONTEND = "프론트엔드";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    public MemberGroup(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getGroupName() {
        if (this.name.contains(BACKEND)) {
            return BACKEND;
        }
        if (this.name.contains(FRONTEND)) {
            return FRONTEND;
        }

        return null;
    }
}
