package pl.kubaretip.cpo.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderStatusDTO {

    private StatusDTO status;
    private String statusDate;

}
