package pl.kubaretip.cpo.api.web.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class EditCategoryRequest extends NewCategoryRequest {

    @NotNull(message = "{validation.category.id.notNull}")
    private Long id;

}
