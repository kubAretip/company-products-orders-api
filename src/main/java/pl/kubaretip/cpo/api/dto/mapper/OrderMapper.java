package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.*;
import pl.kubaretip.cpo.api.domain.Order;
import pl.kubaretip.cpo.api.dto.OrderDTO;
import pl.kubaretip.cpo.api.web.rest.request.AcceptOrderRequest;
import pl.kubaretip.cpo.api.web.rest.request.RejectOrderRequest;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {OrderProductMapper.class})
public interface OrderMapper {

    @Named("mapToOrderDTO")
    OrderDTO mapToOrderDTO(Order entity);

    @Mapping(ignore = true, target = "client.addresses")
    OrderDTO mapToOrderDTOWithoutClientAddresses(Order order);

    @IterableMapping(qualifiedByName = "mapToOrderDTO")
    List<OrderDTO> mapToOrderDTOList(List<Order> orderList);

    OrderDTO mapRejectOrderRequestToOrderDTO(RejectOrderRequest request);

    @Mapping(target = "orderProducts", source = "orderExecutors", qualifiedByName = "acceptOrderProductsToOrderProductsDTO")
    OrderDTO mapAcceptOrderRequestToOrderDTO(AcceptOrderRequest request);

}
