package pl.kubaretip.cpo.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kubaretip.cpo.api.validation.groups.Pk;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;

@Getter
@Setter
@NoArgsConstructor
public class OrderProductDTO {

    private Long id;

    @NotNull
    private Integer quantity;

    @NotNull
    @Valid
    @ConvertGroup(to = Pk.class)
    private ProductDTO product;

    public OrderProductDTO(Integer quantity, ProductDTO product) {
        this.quantity = quantity;
        this.product = product;
    }
}
