package pl.kubaretip.cpo.api.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pl.kubaretip.cpo.api.domain.Category;
import pl.kubaretip.cpo.api.dto.CategoryDTO;
import pl.kubaretip.cpo.api.web.rest.request.EditCategoryRequest;
import pl.kubaretip.cpo.api.web.rest.request.NewCategoryRequest;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-06T10:37:16+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.21 (Eclipse Adoptium)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDTO mapToDTO(Category entity) {
        if ( entity == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId( entity.getId() );
        categoryDTO.setName( entity.getName() );

        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> mapToDTOList(List<Category> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CategoryDTO> list = new ArrayList<CategoryDTO>( entityList.size() );
        for ( Category category : entityList ) {
            list.add( mapToDTO( category ) );
        }

        return list;
    }

    @Override
    public CategoryDTO mapToCategoryDTOOnlyWithId(Long categoryId) {
        if ( categoryId == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId( categoryId );

        return categoryDTO;
    }

    @Override
    public CategoryDTO mapNewCategoryRequestToCategoryDTO(NewCategoryRequest request) {
        if ( request == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setName( request.getName() );

        return categoryDTO;
    }

    @Override
    public CategoryDTO mapEditCategoryRequestToCategoryDTO(EditCategoryRequest request) {
        if ( request == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId( request.getId() );
        categoryDTO.setName( request.getName() );

        return categoryDTO;
    }
}
