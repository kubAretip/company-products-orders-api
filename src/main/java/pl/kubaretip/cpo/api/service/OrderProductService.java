package pl.kubaretip.cpo.api.service;

import pl.kubaretip.cpo.api.domain.OrderProduct;

import java.util.List;

public interface OrderProductService {
    List<OrderProduct> findAllOrderProductByExecutorId(long executorId);
}
