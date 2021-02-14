package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kubaretip.cpo.api.domain.Order;
import pl.kubaretip.cpo.api.dto.OrderDTO;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(ignore = true, target = "client.deliveryAddresses")
    OrderDTO mapToDTO(Order entity);

}
