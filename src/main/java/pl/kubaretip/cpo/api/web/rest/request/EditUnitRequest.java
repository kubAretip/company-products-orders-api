package pl.kubaretip.cpo.api.web.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class EditUnitRequest extends NewUnitRequest {

    @NotNull(message = "{validation.unit.id.notNull}")
    private Long id;
}
