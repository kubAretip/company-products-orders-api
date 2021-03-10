package pl.kubaretip.cpo.api.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pl.kubaretip.cpo.api.domain.Category;
import pl.kubaretip.cpo.api.dto.CategoryDTO;
import pl.kubaretip.cpo.api.exception.AlreadyExistsException;
import pl.kubaretip.cpo.api.exception.NotFoundException;
import pl.kubaretip.cpo.api.repository.CategoryRepository;
import pl.kubaretip.cpo.api.service.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("exception.category.notFound", new Object[]{categoryId}));
    }

    @Override
    public Category createCategory(CategoryDTO categoryDTO) {

        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw categoryWithNameAlreadyExists(categoryDTO.getName());
        }

        var newCategory = new Category();
        newCategory.setName(StringUtils.capitalize(categoryDTO.getName().toLowerCase()));
        categoryRepository.save(newCategory);

        return newCategory;
    }

    @Override
    public void markCategoryAsDeleted(long categoryId) {
        var category = getCategoryById(categoryId);
        category.setDeleted(true);
        categoryRepository.save(category);
    }

    @Override
    public Category modifyCategory(CategoryDTO categoryDTO) {

        var category = getCategoryById(categoryDTO.getId());

        categoryRepository.findByName(categoryDTO.getName())
                .ifPresent(exists -> {
                    if (!exists.getId().equals(categoryDTO.getId())) {
                        throw categoryWithNameAlreadyExists(categoryDTO.getName());
                    }
                });

        category.setName(StringUtils.capitalize(categoryDTO.getName().toLowerCase()));
        categoryRepository.save(category);

        return category;
    }

    private AlreadyExistsException categoryWithNameAlreadyExists(String categoryName) {
        return new AlreadyExistsException("exception.category.alreadyExists.name", new Object[]{categoryName});
    }

}






















