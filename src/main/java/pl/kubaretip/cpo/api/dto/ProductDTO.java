package pl.kubaretip.cpo.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kubaretip.cpo.api.validation.groups.Pk;
import pl.kubaretip.cpo.api.validation.groups.Update;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.ConvertGroup;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

    @NotNull(message = "{validation.product.id.notNull.message}", groups = {Update.class})
    private Long id;

    @NotBlank(message = "{validation.product.name.notBlank.message}")
    @Size(min = 2, max = 150, message = "{validation.product.name.size.message}")
    private String name;

    @Valid
    @NotNull(message = "{validation.product.category.notNull.message}")
    @ConvertGroup(to = Pk.class)
    private CategoryDTO category;

    @Valid
    @NotNull(message = "{validation.product.unit.notNull.message}")
    @ConvertGroup(to = Pk.class)
    private UnitDTO unit;


}
