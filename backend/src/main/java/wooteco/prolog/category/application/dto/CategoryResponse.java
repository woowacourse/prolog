package wooteco.prolog.category.application.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.category.domain.Category;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class CategoryResponse {
    private Long id;
    private String name;

    public static CategoryResponse of(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}