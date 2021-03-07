package pl.kubaretip.cpo.api.web.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class AcceptOrderRequest {

    @NotNull(message = "{validation.order.id.notNull}")
    private Long id;

    @NotEmpty(message = "{validation.order.accept.orderExecutors.notEmpty}")
    private List<@Valid OrderProductExecutors> orderExecutors;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class OrderProductExecutors {

        @NotNull(message = "{validation.order.accept.executorId.notNull}")
        private Long executorId;

        @NotNull(message = "{validation.order.accept.orderProductId.notNull}")
        private Long orderProductId;
    }

}
