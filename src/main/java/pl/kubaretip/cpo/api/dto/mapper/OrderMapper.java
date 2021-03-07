package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.kubaretip.cpo.api.domain.Order;
import pl.kubaretip.cpo.api.dto.OrderDTO;
import pl.kubaretip.cpo.api.web.rest.request.RejectOrderRequest;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Named("mapToOrderDTO")
    OrderDTO mapToOrderDTO(Order entity);

    @Mapping(ignore = true, target = "client.addresses")
    OrderDTO mapToOrderDTOWithoutClientAddresses(Order order);

    @IterableMapping(qualifiedByName = "mapToOrderDTO")
    List<OrderDTO> mapToOrderDTOList(List<Order> orderList);

    @Mapping(target = "supervisor", ignore = true)
    @Mapping(target = "orderProducts", ignore = true)
    @Mapping(target = "marketer", ignore = true)
    @Mapping(target = "deliveryAddress", ignore = true)
    @Mapping(target = "client", ignore = true)
    OrderDTO mapRejectOrderRequestToOrderDTO(RejectOrderRequest request);

}
