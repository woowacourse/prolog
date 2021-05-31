package wooteco.prolog.mission.application.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.mission.domain.Mission;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class MissionResponse {
    private Long id;
    private String name;

    public static MissionResponse of(Mission mission) {
        return new MissionResponse(mission.getId(), mission.getName());
    }
}