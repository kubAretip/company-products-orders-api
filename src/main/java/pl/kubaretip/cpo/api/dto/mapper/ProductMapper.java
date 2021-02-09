package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.kubaretip.cpo.api.domain.Product;
import pl.kubaretip.cpo.api.dto.ProductDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product mapToEntity(ProductDTO dto);

    ProductDTO mapToDTO(Product entity);

    List<ProductDTO> mapToListDTO(List<Product> entityList);

}
