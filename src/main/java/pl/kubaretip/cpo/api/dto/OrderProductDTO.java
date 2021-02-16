package pl.kubaretip.cpo.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderProductDTO {

    private Long id;
    private Integer quantity;
    private ProductDTO product;
    private UserDTO executor;

    public OrderProductDTO(Integer quantity, ProductDTO product) {
        this.quantity = quantity;
        this.product = product;
    }
}
