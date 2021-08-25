package wooteco.prolog.mission.application.dto;

import lombok.Getter;

@Getter
public class MissionRequest {

    private String name;
    private Long levelId;

    public MissionRequest() {
    }

    public MissionRequest(String name, Long levelId) {
        this.name = name;
        this.levelId = levelId;
    }
}
