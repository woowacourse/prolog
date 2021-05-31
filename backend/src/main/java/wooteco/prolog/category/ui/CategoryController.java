package wooteco.prolog.category.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.prolog.category.application.CategoryService;
import wooteco.prolog.category.application.dto.CategoryRequest;
import wooteco.prolog.category.application.dto.CategoryResponse;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public ResponseEntity<List<CategoryResponse>> showCategories() {
        List<CategoryResponse> responses = categoryService.findAll();
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.create(categoryRequest);
        return ResponseEntity.ok(categoryResponse);
    }
}