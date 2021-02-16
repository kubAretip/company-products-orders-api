package pl.kubaretip.cpo.api.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kubaretip.cpo.api.dto.OrderDTO;
import pl.kubaretip.cpo.api.dto.mapper.OrderMapper;
import pl.kubaretip.cpo.api.service.OrderService;
import pl.kubaretip.cpo.api.util.ExceptionUtils;
import pl.kubaretip.cpo.api.web.rest.request.AcceptOrderRequest;
import pl.kubaretip.cpo.api.web.rest.request.NewOrderRequest;
import pl.kubaretip.cpo.api.web.rest.request.RejectOrderRequest;

import javax.validation.Valid;

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

    @PostMapping
    public ResponseEntity<OrderDTO> createNewOrder(@Valid @RequestBody NewOrderRequest request,
                                                   UriComponentsBuilder uriComponentsBuilder) {
        var order = orderService.createNewOrder(request.toDTO());
        var location = uriComponentsBuilder.path("/orders/{id}")
                .buildAndExpand(order.getId()).toUri();
        return ResponseEntity.created(location).body(orderMapper.mapToDTO(order));
    }

    @PatchMapping("/{id}/accept")
    public ResponseEntity<Void> acceptOrder(@PathVariable("id") long orderId,
                                            @Valid @RequestBody AcceptOrderRequest request) {

        if (orderId != request.getId()) {
            throw exceptionUtils.pathIdNotEqualsBodyId();
        }

        orderService.acceptOrder(request.toDTO());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<Void> rejectOrder(@PathVariable("id") long orderId,
                                            @Valid @RequestBody RejectOrderRequest request) {
        if (orderId != request.getId()) {
            throw exceptionUtils.pathIdNotEqualsBodyId();
        }
        orderService.rejectOrder(request.toDTO());
        return ResponseEntity.noContent().build();
    }


}
