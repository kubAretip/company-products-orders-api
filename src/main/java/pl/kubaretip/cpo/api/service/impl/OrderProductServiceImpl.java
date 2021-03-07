package pl.kubaretip.cpo.api.service.impl;

import org.springframework.stereotype.Service;
import pl.kubaretip.cpo.api.constants.AuthoritiesConstants;
import pl.kubaretip.cpo.api.domain.OrderProduct;
import pl.kubaretip.cpo.api.repository.OrderProductRepository;
import pl.kubaretip.cpo.api.service.OrderProductService;
import pl.kubaretip.cpo.api.service.UserService;

import java.util.List;

@Service
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final UserService userService;

    public OrderProductServiceImpl(OrderProductRepository orderProductRepository,
                                   UserService userService) {
        this.orderProductRepository = orderProductRepository;
        this.userService = userService;
    }

    @Override
    public List<OrderProduct> findAllOrderProductByExecutorId(long executorId) {
        var executor = userService.findUserByIdAndAuthority(executorId, AuthoritiesConstants.ROLE_EXECUTOR);
        return orderProductRepository.findByExecutor(executor);
    }


}
