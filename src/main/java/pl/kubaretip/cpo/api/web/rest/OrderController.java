package pl.kubaretip.cpo.api.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kubaretip.cpo.api.constants.AuthoritiesConstants;
import pl.kubaretip.cpo.api.dto.OrderDTO;
import pl.kubaretip.cpo.api.dto.mapper.OrderMapper;
import pl.kubaretip.cpo.api.service.OrderService;
import pl.kubaretip.cpo.api.util.ExceptionUtils;
import pl.kubaretip.cpo.api.util.SecurityUtils;
import pl.kubaretip.cpo.api.web.rest.request.AcceptOrderRequest;
import pl.kubaretip.cpo.api.web.rest.request.NewOrderRequest;
import pl.kubaretip.cpo.api.web.rest.request.RejectOrderRequest;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final ExceptionUtils exceptionUtils;

    public OrderController(OrderService orderService,
                           OrderMapper orderMapper,
                           ExceptionUtils exceptionUtils) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.exceptionUtils = exceptionUtils;
    }

    @Secured(AuthoritiesConstants.Code.MARKETER)
    @PostMapping
    public ResponseEntity<OrderDTO> createNewOrder(@Valid @RequestBody NewOrderRequest request,
                                                   UriComponentsBuilder uriComponentsBuilder,
                                                   Authentication authentication) {
        var order = orderService.createNewOrder(orderMapper.mapNewOrderRequestToOrderDTO(request),
                SecurityUtils.principal(authentication.getPrincipal()).getId());
        var location = uriComponentsBuilder.path("/orders/{id}")
                .buildAndExpand(order.getId()).toUri();
        return ResponseEntity.created(location).body(orderMapper.mapToOrderDTOWithoutClientAddresses(order));
    }

    @Secured(AuthoritiesConstants.Code.SUPERVISOR)
    @PatchMapping("/{id}/accept")
    public ResponseEntity<Void> acceptOrder(@PathVariable("id") long orderId,
                                            @Valid @RequestBody AcceptOrderRequest request,
                                            Authentication authentication) {

        if (orderId != request.getId()) {
            throw exceptionUtils.pathIdNotEqualsBodyId();
        }

        orderService.acceptOrder(orderMapper.mapAcceptOrderRequestToOrderDTO(request),
                SecurityUtils.principal(authentication.getPrincipal()).getId());
        return ResponseEntity.noContent().build();
    }

    @Secured(AuthoritiesConstants.Code.SUPERVISOR)
    @PatchMapping("/{id}/reject")
    public ResponseEntity<Void> rejectOrder(@PathVariable("id") long orderId,
                                            @Valid @RequestBody RejectOrderRequest request,
                                            Authentication authentication) {
        if (orderId != request.getId()) {
            throw exceptionUtils.pathIdNotEqualsBodyId();
        }
        var orderDTO = orderMapper.mapRejectOrderRequestToOrderDTO(request);
        orderService.rejectOrder(orderDTO, SecurityUtils.principal(authentication.getPrincipal()).getId());
        return ResponseEntity.noContent().build();
    }

    @Secured(AuthoritiesConstants.Code.SUPERVISOR)
    @GetMapping("/pending-acceptance")
    public ResponseEntity<List<OrderDTO>> ordersPendingForAccept() {
        return ResponseEntity.ok()
                .body(orderMapper.mapToOrderDTOList(orderService.getOrdersWithPendingSupervisorAcceptance()));
    }

    @Secured(AuthoritiesConstants.Code.USER)
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("id") long orderId) {
        var orderById = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderMapper.mapToOrderDTO(orderById));
    }


}
