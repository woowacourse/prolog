package wooteco.prolog.session.application.dto;

import wooteco.prolog.session.domain.Level;

public class LevelRequest {

    private String name;

    public LevelRequest() {
    }

    public LevelRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Level toEntity() {
        return new Level(this.name);
    }
}
