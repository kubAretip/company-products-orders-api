package pl.kubaretip.cpo.api.web.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kubaretip.cpo.api.dto.UnitDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class NewUnitRequest {

    @NotBlank(message = "{validation.unit.name.notBlank}")
    @Size(min = 3, max = 35, message = "{validation.unit.name.size}")
    protected String name;

    @NotBlank(message = "{validation.unit.symbol.notBlank}")
    @Size(min = 1, max = 10, message = "{validation.unit.symbol.size}")
    protected String symbol;

    public UnitDTO toDTO() {
        return new UnitDTO(this.name, this.symbol);
    }


}
