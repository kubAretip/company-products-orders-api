package pl.kubaretip.cpo.api.web.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kubaretip.cpo.api.dto.UnitDTO;
import pl.kubaretip.cpo.api.validation.groups.Pk;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class EditUnitRequest extends NewUnitRequest {

    @NotNull(message = "{validation.unit.id.notNull}", groups = {Pk.class})
    private Long id;

    @Override
    public UnitDTO toDTO() {
        return new UnitDTO(this.id, this.name, this.symbol);
    }
}
