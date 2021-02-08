package pl.kubaretip.cpo.api.service;

import pl.kubaretip.cpo.api.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();

    CategoryDTO findById(long categoryId);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    void markCategoryAsDeleted(long categoryId);

    CategoryDTO modifyCategory(long categoryId, CategoryDTO categoryDTO);
}
