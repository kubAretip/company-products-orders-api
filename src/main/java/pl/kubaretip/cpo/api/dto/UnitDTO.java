package pl.kubaretip.cpo.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UnitDTO {

    private Long id;

    @NotBlank(message = "{validation.unit.name.notBlank.message}")
    @Size(min = 3, max = 35, message = "{validation.unit.name.size.message}")
    private String name;

    @NotBlank(message = "{validation.unit.symbol.notBlank.message}")
    @Size(min = 1, max = 10, message = "{validation.unit.symbol.size.message}")
    private String symbol;

}