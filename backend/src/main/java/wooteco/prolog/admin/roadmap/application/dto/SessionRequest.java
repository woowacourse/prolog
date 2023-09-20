package wooteco.prolog.admin.roadmap.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SessionRequest {

    private String name;

    public SessionRequest(final String name) {
        this.name = name;
    }
}
