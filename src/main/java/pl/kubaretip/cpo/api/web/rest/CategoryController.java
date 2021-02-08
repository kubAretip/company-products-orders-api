package pl.kubaretip.cpo.api.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kubaretip.cpo.api.dto.CategoryDTO;
import pl.kubaretip.cpo.api.exception.InvalidDataException;
import pl.kubaretip.cpo.api.service.CategoryService;
import pl.kubaretip.cpo.api.util.Translator;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final Translator translator;

    public CategoryController(CategoryService categoryService,
                              Translator translator) {
        this.categoryService = categoryService;
        this.translator = translator;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAll() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable("id") long categoryId) {
        return ResponseEntity.ok(categoryService.findById(categoryId));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                                      UriComponentsBuilder uriComponentsBuilder) {
        var category = categoryService.createCategory(categoryDTO);
        var locationURI = uriComponentsBuilder.path("/categories/{id}")
                .buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(locationURI).body(category);
    }


    @PatchMapping(path = "/{id}", params = {"remove"})
    public ResponseEntity<Void> markCategoryAsDeleted(@PathVariable("id") long categoryId,
                                                      @RequestParam("remove") boolean remove) {

        categoryService.markCategoryAsDeleted(categoryId);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping(path = "/{id}")
    public ResponseEntity<CategoryDTO> editCategory(@PathVariable("id") long categoryId,
                                                    @Valid @RequestBody CategoryDTO categoryDTO) {

        if (categoryDTO.getId() == null || categoryDTO.getId() != categoryId) {
            throw new InvalidDataException(translator.translate("exception.common.badRequest.title"),
                    translator.translate("exception.common.pathIdNotEqualsBodyId.message"));
        }
        return ResponseEntity.ok(categoryService.modifyCategory(categoryId, categoryDTO));
    }


}
