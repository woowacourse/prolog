package wooteco.studylog.log.domain;

public class Category {
    private final Long categoryId;
    private final String name;

    public Category(Long categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }
}
