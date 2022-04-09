package wooteco.prolog.session.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MissionRequest {

    private String name;
    private Long sessionId;
}
