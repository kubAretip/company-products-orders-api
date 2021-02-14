package pl.kubaretip.cpo.api.service;

import pl.kubaretip.cpo.api.constants.StatusConstants;
import pl.kubaretip.cpo.api.domain.OrderStatus;

public interface OrderStatusService {
    OrderStatus getOrderStatus(StatusConstants status);
}
