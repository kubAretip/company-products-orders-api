package pl.kubaretip.cpo.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kubaretip.cpo.api.validation.groups.Pk;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {

    @NotNull(message = "{validation.category.id.notNull}", groups = {Pk.class})
    private Long id;

    @NotBlank(message = "{validation.category.name.notBlank}")
    @Size(min = 1, max = 150, message = "{validation.category.name.size}")
    private String name;
}
