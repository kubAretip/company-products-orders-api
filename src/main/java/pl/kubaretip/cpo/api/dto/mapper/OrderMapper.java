package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.kubaretip.cpo.api.domain.Order;
import pl.kubaretip.cpo.api.dto.OrderDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Named("mapToOrderDTO")
    OrderDTO mapToOrderDTO(Order entity);

    @Mapping(ignore = true, target = "client.addresses")
    OrderDTO mapToOrderDTOWithoutClientAddresses(Order order);

    @IterableMapping(qualifiedByName = "mapToOrderDTO")
    List<OrderDTO> mapToOrderDTOList(List<Order> orderList);

}
