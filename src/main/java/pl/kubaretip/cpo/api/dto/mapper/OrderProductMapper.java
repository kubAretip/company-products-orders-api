package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.kubaretip.cpo.api.domain.OrderProduct;
import pl.kubaretip.cpo.api.dto.OrderProductDTO;

@Mapper(componentModel = "spring")
public interface OrderProductMapper {

    OrderProductDTO orderProductToOrderProductDTO(OrderProduct entity);

}
