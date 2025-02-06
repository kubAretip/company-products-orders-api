package pl.kubaretip.cpo.api.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kubaretip.cpo.api.domain.Product;
import pl.kubaretip.cpo.api.dto.ProductDTO;
import pl.kubaretip.cpo.api.web.rest.request.EditProductRequest;
import pl.kubaretip.cpo.api.web.rest.request.NewProductRequest;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-06T10:37:16+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.21 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Autowired
    private UnitMapper unitMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ProductDTO mapToDTO(Product entity) {
        if ( entity == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setId( entity.getId() );
        productDTO.setName( entity.getName() );
        productDTO.setCategory( categoryMapper.mapToDTO( entity.getCategory() ) );
        productDTO.setUnit( unitMapper.mapToDTO( entity.getUnit() ) );

        return productDTO;
    }

    @Override
    public List<ProductDTO> mapToListDTO(List<Product> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ProductDTO> list = new ArrayList<ProductDTO>( entityList.size() );
        for ( Product product : entityList ) {
            list.add( mapToDTO( product ) );
        }

        return list;
    }

    @Override
    public ProductDTO mapToProductDTOOnlyWithId(Long productId) {
        if ( productId == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setId( productId );

        return productDTO;
    }

    @Override
    public ProductDTO mapNewProductRequestToProductDTO(NewProductRequest request) {
        if ( request == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setCategory( categoryMapper.mapToCategoryDTOOnlyWithId( request.getCategoryId() ) );
        productDTO.setUnit( unitMapper.mapToUnitDTOOnlyWithId( request.getUnitId() ) );
        productDTO.setName( request.getName() );

        return productDTO;
    }

    @Override
    public ProductDTO mapEditProductRequestToProductDTO(EditProductRequest request) {
        if ( request == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setCategory( categoryMapper.mapToCategoryDTOOnlyWithId( request.getCategoryId() ) );
        productDTO.setUnit( unitMapper.mapToUnitDTOOnlyWithId( request.getUnitId() ) );
        productDTO.setId( request.getId() );
        productDTO.setName( request.getName() );

        return productDTO;
    }
}
