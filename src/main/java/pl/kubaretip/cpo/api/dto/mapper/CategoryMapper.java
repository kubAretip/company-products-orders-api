package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pl.kubaretip.cpo.api.domain.Category;
import pl.kubaretip.cpo.api.dto.CategoryDTO;
import pl.kubaretip.cpo.api.web.rest.request.EditCategoryRequest;
import pl.kubaretip.cpo.api.web.rest.request.NewCategoryRequest;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    CategoryDTO mapToDTO(Category entity);

    List<CategoryDTO> mapToDTOList(List<Category> entityList);

    @Named("mapToCategoryDTOOnlyWithId")
    @Mapping(target = "id", source = "categoryId")
    CategoryDTO mapToCategoryDTOOnlyWithId(Long categoryId);

    CategoryDTO mapNewCategoryRequestToCategoryDTO(NewCategoryRequest request);

    CategoryDTO mapEditCategoryRequestToCategoryDTO(EditCategoryRequest request);
}
