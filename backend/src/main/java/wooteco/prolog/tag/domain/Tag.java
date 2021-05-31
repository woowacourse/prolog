package wooteco.prolog.tag.domain;

public class Tag {
    private final Long id;
    private final String name;

    public Tag(String name) {
        this(null, name);
    }

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

    public boolean sameAs(String name) {
        return this.name.equals(name);
    }
}
