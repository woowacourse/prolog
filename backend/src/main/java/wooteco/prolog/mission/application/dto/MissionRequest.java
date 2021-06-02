package wooteco.prolog.mission.application.dto;

public class MissionRequest {

    private String name;

    public MissionRequest() {

    }

    public MissionRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
