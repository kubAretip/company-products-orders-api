package pl.kubaretip.cpo.api.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kubaretip.cpo.api.dto.CategoryDTO;
import pl.kubaretip.cpo.api.dto.mapper.CategoryMapper;
import pl.kubaretip.cpo.api.service.CategoryService;
import pl.kubaretip.cpo.api.util.ExceptionUtils;
import pl.kubaretip.cpo.api.web.rest.request.EditCategoryRequest;
import pl.kubaretip.cpo.api.web.rest.request.NewCategoryRequest;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ExceptionUtils exceptionUtils;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService,
                              ExceptionUtils exceptionUtils,
                              CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.exceptionUtils = exceptionUtils;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAll() {
        return ResponseEntity.ok(categoryMapper.mapToDTOList(categoryService.getAllCategories()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable("id") long categoryId) {
        return ResponseEntity.ok(categoryMapper.mapToDTO(categoryService.getCategoryById(categoryId)));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody NewCategoryRequest request,
                                                      UriComponentsBuilder uriComponentsBuilder) {
        var category = categoryService.createCategory(request.toDTO());
        var locationURI = uriComponentsBuilder.path("/categories/{id}")
                .buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(locationURI).body(categoryMapper.mapToDTO(category));
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> markCategoryAsDeleted(@PathVariable("id") long categoryId) {
        categoryService.markCategoryAsDeleted(categoryId);
        return ResponseEntity.accepted().build();
    }


    @PatchMapping(path = "/{id}")
    public ResponseEntity<CategoryDTO> editCategory(@PathVariable("id") long categoryId,
                                                    @Valid @RequestBody EditCategoryRequest request) {
        if (request.getId() != categoryId) {
            throw exceptionUtils.pathIdNotEqualsBodyId();
        }
        var resultCategory = categoryService.modifyCategory(request.toDTO());
        return ResponseEntity.ok(categoryMapper.mapToDTO(resultCategory));
    }


}
