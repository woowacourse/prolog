package wooteco.prolog.filter.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.category.application.dto.CategoryResponse;
import wooteco.prolog.filter.application.dto.FilterResponse;
import wooteco.prolog.tag.dto.TagResponse;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/filters")
public class FilterController {

    @GetMapping
    public ResponseEntity<FilterResponse> showAll() {
        List<CategoryResponse> categoryResponses = Arrays.asList(
                new CategoryResponse(1L, "포모의 궁금한 게 있습니다"),
                new CategoryResponse(2L, "코다는 눈을 감자 좋아해"),
                new CategoryResponse(3L, "바둑 미션"),
                new CategoryResponse(4L, "포모의 조선정복기"),
                new CategoryResponse(5L, "포모의 모포말기")
        );

        List<TagResponse> tagResponses = Arrays.asList(
                new TagResponse(1L, "모포"),
                new TagResponse(2L, "포모"),
                new TagResponse(3L, "국모")
        );

        return ResponseEntity.ok().body(new FilterResponse(categoryResponses, tagResponses));
    }
}
