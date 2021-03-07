package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.*;
import pl.kubaretip.cpo.api.domain.Product;
import pl.kubaretip.cpo.api.dto.ProductDTO;
import pl.kubaretip.cpo.api.web.rest.request.EditProductRequest;
import pl.kubaretip.cpo.api.web.rest.request.NewProductRequest;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UnitMapper.class, CategoryMapper.class})
public interface ProductMapper {

    ProductDTO mapToDTO(Product entity);

    List<ProductDTO> mapToListDTO(List<Product> entityList);

    @Named("mapToProductDTOOnlyWithId")
    @Mapping(target = "id", source = "productId")
    ProductDTO mapToProductDTOOnlyWithId(Long productId);

    @Named("mapNewProductRequestToProductDTO")
    @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapToCategoryDTOOnlyWithId")
    @Mapping(target = "unit", source = "unitId", qualifiedByName = "mapToUnitDTOOnlyWithId")
    ProductDTO mapNewProductRequestToProductDTO(NewProductRequest request);

    @InheritConfiguration(name = "mapNewProductRequestToProductDTO")
    ProductDTO mapEditProductRequestToProductDTO(EditProductRequest request);

}
