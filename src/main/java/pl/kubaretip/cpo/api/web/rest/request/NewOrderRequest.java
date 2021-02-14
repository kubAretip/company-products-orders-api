package pl.kubaretip.cpo.api.web.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kubaretip.cpo.api.dto.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class NewOrderRequest {

    @NotNull
    private Long clientId;

    @NotNull
    private Long deliveryAddressId;

    @NotEmpty
    private List<@Valid ProductInOrder> products;

    public OrderDTO toDTO() {
        var orderProductsDTO = products.stream()
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
    static class ProductInOrder {
        @NotNull
        private Long productId;

        @NotNull
        private Integer quantity;
    }

}
