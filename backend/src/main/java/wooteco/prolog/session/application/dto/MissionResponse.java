package wooteco.prolog.session.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.session.domain.Mission;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MissionResponse {

    private Long id;
    private String name;
    private LevelResponse level;

    public static MissionResponse of(Mission mission) {
        return new MissionResponse(mission.getId(), mission.getName(),
                                   LevelResponse.of(mission.getLevel()));
    }
}
