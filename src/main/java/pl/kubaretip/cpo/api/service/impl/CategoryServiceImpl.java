package pl.kubaretip.cpo.api.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pl.kubaretip.cpo.api.domain.Category;
import pl.kubaretip.cpo.api.dto.CategoryDTO;
import pl.kubaretip.cpo.api.dto.mapper.CategoryMapper;
import pl.kubaretip.cpo.api.exception.AlreadyExistsException;
import pl.kubaretip.cpo.api.exception.NotFoundException;
import pl.kubaretip.cpo.api.repository.CategoryRepository;
import pl.kubaretip.cpo.api.service.CategoryService;
import pl.kubaretip.cpo.api.util.Translator;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final Translator translator;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryMapper categoryMapper,
                               Translator translator) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.translator = translator;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryMapper.mapToDTOList(categoryRepository.findAll());
    }

    @Override
    public CategoryDTO findById(long categoryId) {
        return categoryMapper.mapToDTO(findCategoryById(categoryId));
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw categoryWithNameAlreadyExists(categoryDTO.getName());
        }

        var newCategory = new Category();
        newCategory.setName(StringUtils.capitalize(categoryDTO.getName().toLowerCase()));
        categoryRepository.save(newCategory);

        return categoryMapper.mapToDTO(newCategory);
    }

    @Override
    public void markCategoryAsDeleted(long categoryId) {
        var category = findCategoryById(categoryId);
        category.setDeleted(true);
        categoryRepository.save(category);
    }

    @Override
    public CategoryDTO modifyCategory(long categoryId, CategoryDTO categoryDTO) {

        var category = findCategoryById(categoryId);

        categoryRepository.findByName(categoryDTO.getName())
                .ifPresent(exists -> {
                    if (!exists.getId().equals(categoryDTO.getId())) {
                        throw categoryWithNameAlreadyExists(categoryDTO.getName());
                    }
                });

        category.setName(StringUtils.capitalize(categoryDTO.getName().toLowerCase()));
        categoryRepository.save(category);
        return categoryMapper.mapToDTO(category);
    }

    private AlreadyExistsException categoryWithNameAlreadyExists(String categoryName) {
        return new AlreadyExistsException(translator.translate("exception.common.alreadyExists.title"),
                translator.translate("exception.category.alreadyExists.message", new Object[]{categoryName}));
    }


    Category findCategoryById(long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(translator.translate("exception.common.notFound.title"),
                        translator.translate("exception.category.byIdNotExists.message", new Object[]{categoryId})));
    }

}






















