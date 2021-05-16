package wooteco.studylog.log.web.controller.dto;

import java.util.List;
import java.util.Objects;

public class CategoryResponses {
    private List<CategoryResponse> categories;

    public CategoryResponses() {
    }

    public CategoryResponses(List<CategoryResponse> categories) {
        this.categories = categories;
    }

    public List<CategoryResponse> getCategories() {
        return categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryResponses that = (CategoryResponses) o;
        return Objects.equals(categories, that.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categories);
    }
}
