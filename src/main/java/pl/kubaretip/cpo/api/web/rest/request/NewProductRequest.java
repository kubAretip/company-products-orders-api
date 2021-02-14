package pl.kubaretip.cpo.api.web.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kubaretip.cpo.api.dto.CategoryDTO;
import pl.kubaretip.cpo.api.dto.ProductDTO;
import pl.kubaretip.cpo.api.dto.UnitDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class NewProductRequest {

    @NotBlank(message = "{validation.product.name.notBlank}")
    @Size(min = 2, max = 150, message = "{validation.product.name.size}")
    protected String name;

    @NotNull(message = "{validation.category.id.notNull}")
    protected Long categoryId;

    @NotNull(message = "{validation.unit.id.notNull}")
    protected Long unitId;

    public ProductDTO toDTO() {
        var categoryDTO = new CategoryDTO(this.categoryId);
        var unitDTO = new UnitDTO(this.unitId);
        return new ProductDTO(this.name, categoryDTO, unitDTO);
    }

}
