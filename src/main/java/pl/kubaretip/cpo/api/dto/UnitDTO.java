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
public class UnitDTO {

    @NotNull(message = "{validation.unit.id.notNull}", groups = {Pk.class})
    private Long id;

    @NotBlank(message = "{validation.unit.name.notBlank}")
    @Size(min = 3, max = 35, message = "{validation.unit.name.size}")
    private String name;

    @NotBlank(message = "{validation.unit.symbol.notBlank}")
    @Size(min = 1, max = 10, message = "{validation.unit.symbol.size}")
    private String symbol;

}
