package pl.kubaretip.cpo.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class OrderProductDTO {

    private Long id;
    private Integer quantity;
    private ProductDTO product;
    private UserDTO executor;

}
