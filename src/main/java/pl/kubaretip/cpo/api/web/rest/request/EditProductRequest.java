package pl.kubaretip.cpo.api.web.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kubaretip.cpo.api.dto.CategoryDTO;
import pl.kubaretip.cpo.api.dto.ProductDTO;
import pl.kubaretip.cpo.api.dto.UnitDTO;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class EditProductRequest extends NewProductRequest {

    @NotNull(message = "{validation.product.id.notNull}")
    private Long id;

    @Override
    public ProductDTO toDTO() {
        return new ProductDTO(this.id, this.name, new CategoryDTO(this.categoryId), new UnitDTO(this.unitId));
    }
}
