package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.kubaretip.cpo.api.domain.Category;
import pl.kubaretip.cpo.api.dto.CategoryDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category mapToEntity(CategoryDTO categoryDTO);

    CategoryDTO mapToDTO(Category entity);

    List<CategoryDTO> mapToDTOList(List<Category> entityList);
}
