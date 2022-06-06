package wooteco.prolog.studylog.domain;

public class Badge {

    private final String name;

    public Badge(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Badge{" +
            "name='" + name + '\'' +
            '}';
    }
}
