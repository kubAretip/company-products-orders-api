package pl.kubaretip.cpo.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private ClientDTO client;
    private UserDTO createdBy;
    private UserDTO acceptedBy;
    private AddressDTO deliveryAddress;
    private List<OrderProductDTO> orderProducts;

    public OrderDTO(ClientDTO client, List<OrderProductDTO> orderProducts, AddressDTO deliveryAddress) {
        this.client = client;
        this.orderProducts = orderProducts;
        this.deliveryAddress = deliveryAddress;
    }

}
