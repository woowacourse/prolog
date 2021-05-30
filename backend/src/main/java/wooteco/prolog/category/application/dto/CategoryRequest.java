package wooteco.prolog.category.application.dto;

public class CategoryRequest {

    private String name;

    public CategoryRequest() {

    }

    public CategoryRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
