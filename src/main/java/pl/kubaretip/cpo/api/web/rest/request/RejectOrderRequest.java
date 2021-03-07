package pl.kubaretip.cpo.api.web.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class RejectOrderRequest {

    @NotNull(message = "{validation.order.id.notNull}")
    private Long id;

    @NotBlank(message = "{validation.order.reject.additionalInformation.notBlank}")
    private String additionalInformation;
}
