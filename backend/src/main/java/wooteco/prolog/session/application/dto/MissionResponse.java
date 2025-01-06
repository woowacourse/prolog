package wooteco.prolog.session.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.session.domain.Mission;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MissionResponse {

    private Long id;
    private String name;
    private SessionResponse session;

    public static MissionResponse of(Mission mission) {
        if (mission == null) {
            return null;
        }
        return new MissionResponse(mission.getId(), mission.getName(),
            SessionResponse.of(mission.getSession()));
    }

    public static List<MissionResponse> listOf(List<Mission> missions) {
        return missions.stream()
            .map(MissionResponse::of)
            .collect(Collectors.toList());
    }
}
