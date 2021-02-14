package pl.kubaretip.cpo.api.web.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kubaretip.cpo.api.dto.CategoryDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class NewCategoryRequest {

    @NotBlank(message = "{validation.category.name.notBlank}")
    @Size(min = 1, max = 150, message = "{validation.category.name.size}")
    protected String name;

    public CategoryDTO toDTO() {
        return new CategoryDTO(this.name);
    }
}
