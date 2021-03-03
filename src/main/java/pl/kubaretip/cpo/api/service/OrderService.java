package pl.kubaretip.cpo.api.service;

import pl.kubaretip.cpo.api.domain.Order;
import pl.kubaretip.cpo.api.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    Order createNewOrder(OrderDTO orderDTO);

    void acceptOrder(OrderDTO orderDTO);

    void rejectOrder(OrderDTO orderDTO);

    List<Order> getOrdersWithPendingSupervisorAcceptance();
}
