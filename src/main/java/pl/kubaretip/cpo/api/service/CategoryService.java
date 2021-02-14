package pl.kubaretip.cpo.api.service;

import pl.kubaretip.cpo.api.domain.Category;
import pl.kubaretip.cpo.api.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    Category getCategoryById(long categoryId);

    Category createCategory(CategoryDTO categoryDTO);

    void markCategoryAsDeleted(long categoryId);

    Category modifyCategory(CategoryDTO categoryDTO);
}
