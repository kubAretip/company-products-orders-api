package pl.kubaretip.cpo.api.web.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kubaretip.cpo.api.dto.OrderDTO;

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

    public OrderDTO toDTO() {
        var orderDTO = new OrderDTO();
        orderDTO.setId(this.id);
        orderDTO.setAdditionalInformation(this.additionalInformation);
        return orderDTO;
    }

}
