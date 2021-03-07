package pl.kubaretip.cpo.api.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kubaretip.cpo.api.constants.AuthoritiesConstants;
import pl.kubaretip.cpo.api.dto.OrderProductDTO;
import pl.kubaretip.cpo.api.dto.mapper.OrderProductMapper;
import pl.kubaretip.cpo.api.service.OrderProductService;
import pl.kubaretip.cpo.api.util.SecurityUtils;

import java.util.List;

@RestController
@RequestMapping("/order-products")
public class OrderProductController {

    private final OrderProductService orderProductService;
    private final OrderProductMapper orderProductMapper;

    public OrderProductController(OrderProductService orderProductService,
                                  OrderProductMapper orderProductMapper) {
        this.orderProductService = orderProductService;
        this.orderProductMapper = orderProductMapper;
    }

    @Secured(AuthoritiesConstants.Code.EXECUTOR)
    @GetMapping("/executor")
    public ResponseEntity<List<OrderProductDTO>> getOrderProductsByExecutor(Authentication authentication) {

        var executorId = SecurityUtils.principal(authentication.getPrincipal()).getId();

        var allOrderProductByExecutorId = orderProductService.findAllOrderProductByExecutorId(executorId);

        return ResponseEntity.ok()
                .body(orderProductMapper.mapOrderProductListToOrderProductDTOList(allOrderProductByExecutorId));
    }


}
