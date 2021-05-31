package wooteco.prolog.filter.application.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.category.application.dto.CategoryResponse;
import wooteco.prolog.tag.dto.TagResponse;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class FilterResponse {
    private List<CategoryResponse> missions;
    private List<TagResponse> tags;
}
