package wooteco.prolog.mission.domain;

public class Mission {
    private Long id;
    private String name;

    public Mission(String name) {
        this(null, name);
    }

    public Mission(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
