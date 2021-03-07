package pl.kubaretip.cpo.api.web.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
