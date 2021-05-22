package wooteco.prolog.post.domain;

public class Tag {
    private final Long id;
    private final String name;

    public Tag(Long id, String name) {
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
