package pl.kubaretip.cpo.api.service;

import pl.kubaretip.cpo.api.domain.Order;
import pl.kubaretip.cpo.api.dto.OrderDTO;

public interface OrderService {
    Order createNewOrder(OrderDTO orderDTO);
}
