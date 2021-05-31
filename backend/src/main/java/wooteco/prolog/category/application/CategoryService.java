package wooteco.prolog.category.application;

import org.springframework.stereotype.Service;
import wooteco.prolog.category.application.dto.CategoryRequest;
import wooteco.prolog.category.application.dto.CategoryResponse;
import wooteco.prolog.category.dao.CategoryDao;
import wooteco.prolog.category.domain.Category;
import wooteco.prolog.category.exception.CategoryNotFoundException;
import wooteco.prolog.category.exception.DuplicateCategoryException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryDao categoryDao;

    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public CategoryResponse create(CategoryRequest categoryRequest) {
        validateName(categoryRequest.getName());

        Category category = new Category(categoryRequest.getName());
        return CategoryResponse.of(categoryDao.insert(category));
    }

    private void validateName(String name) {
        categoryDao.findByName(name)
                .ifPresent((category) -> {
                    throw new DuplicateCategoryException();
                });
    }

    public List<CategoryResponse> findAll() {
        return categoryDao.findAll()
                .stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }

    public CategoryResponse findById(Long id) {
        Category category = categoryDao.findById(id)
                .orElseThrow(() -> {
                    throw new CategoryNotFoundException();
                });
        return CategoryResponse.of(category);
    }
}
