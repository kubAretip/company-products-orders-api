package pl.kubaretip.cpo.api.service;

import pl.kubaretip.cpo.api.domain.Order;
import pl.kubaretip.cpo.api.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    Order createNewOrder(OrderDTO orderDTO, long marketerId);

    void acceptOrder(OrderDTO orderDTO, long supervisorId);

    void rejectOrder(OrderDTO orderDTO, long supervisorId);

    Order getOrderById(long orderId);

    List<Order> getOrdersWithPendingSupervisorAcceptance();
}
