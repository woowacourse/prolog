package wooteco.prolog.mission.application.dto;

import lombok.Getter;
import wooteco.prolog.mission.domain.Mission;

@Getter
public class MissionRequest {

    private String name;

    public MissionRequest() {

    }

    public MissionRequest(String name) {
        this.name = name;
    }

    public Mission toEntity(){
        return new Mission(this.name);
    }
}
