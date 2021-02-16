package pl.kubaretip.cpo.api.web.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kubaretip.cpo.api.dto.OrderDTO;
import pl.kubaretip.cpo.api.dto.OrderProductDTO;
import pl.kubaretip.cpo.api.dto.UserDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

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
    private static class OrderProductExecutors {

        @NotNull(message = "{validation.order.accept.executorId.notNull}")
        private Long executorId;

        @NotNull(message = "{validation.order.accept.orderProductId.notNull}")
        private Long orderProductId;
    }

    public OrderDTO toDTO() {
        var order = new OrderDTO();
        order.setId(this.id);
        var orderProducts = orderExecutors.stream()
                .map(orderProductExecutor -> {
                    var orderProductDTO = new OrderProductDTO();
                    orderProductDTO.setId(orderProductExecutor.getOrderProductId());
                    orderProductDTO.setExecutor(new UserDTO(orderProductExecutor.getExecutorId()));
                    return orderProductDTO;
                }).collect(Collectors.toList());
        order.setOrderProducts(orderProducts);
        return order;
    }


}
