package pl.kubaretip.cpo.api.web.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kubaretip.cpo.api.dto.*;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class NewOrderRequest {

    @NotNull(message = "{validation.order.clientId.notNull}")
    private Long clientId;

    @NotNull(message = "{validation.order.deliveryAddressId.notNull}")
    private Long deliveryAddressId;

    @NotEmpty(message = "{validation.order.products.notEmpty}")
    private List<@Valid ProductInOrder> products;

    public OrderDTO toDTO() {
        var orderProductsDTO = this.products.stream()
                .map(productInOrder -> new OrderProductDTO(productInOrder.getQuantity(),
                        new ProductDTO(productInOrder.getProductId())))
                .collect(Collectors.toList());
        var clientDTO = new ClientDTO(this.clientId);
        var deliveryAddressDTO = new AddressDTO(this.deliveryAddressId);
        return new OrderDTO(clientDTO, orderProductsDTO, deliveryAddressDTO);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private static class ProductInOrder {

        @NotNull(message = "{validation.order.products.productId.notNull}")
        private Long productId;

        @NotNull(message = "{validation.order.products.quantity.notNull}")
        @DecimalMin(value = "1", message = "{validation.order.products.quantity.decimalMin}")
        private Integer quantity;
    }

}
