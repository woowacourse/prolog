package wooteco.prolog.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberGroup {

    private static final String BACKEND = "백엔드";
    private static final String FRONTEND = "프론트엔드";
    private static final String ANDROID = "안드로이드";

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

    public static List<String> parts() {
        return Arrays.asList(BACKEND, FRONTEND, ANDROID);
    }

    public String part() {
        if (this.name.contains(BACKEND)) {
            return BACKEND;
        }
        if (this.name.contains(FRONTEND)) {
            return FRONTEND;
        }
        if (this.name.contains(ANDROID)) {
            return ANDROID;
        }

        return null;
    }

}
