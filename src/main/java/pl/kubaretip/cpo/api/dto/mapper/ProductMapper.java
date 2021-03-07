package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pl.kubaretip.cpo.api.domain.Product;
import pl.kubaretip.cpo.api.dto.ProductDTO;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductDTO mapToDTO(Product entity);

    List<ProductDTO> mapToListDTO(List<Product> entityList);

    @Named("mapToProductDTOOnlyWithId")
    @Mapping(target = "id", source = "productId")
    ProductDTO mapToProductDTOOnlyWithId(Long productId);

}
