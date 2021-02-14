package pl.kubaretip.cpo.api.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kubaretip.cpo.api.dto.OrderDTO;
import pl.kubaretip.cpo.api.dto.mapper.OrderMapper;
import pl.kubaretip.cpo.api.service.OrderService;
import pl.kubaretip.cpo.api.web.rest.request.NewOrderRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createNewOrder(@Valid @RequestBody NewOrderRequest request,
                                                   UriComponentsBuilder uriComponentsBuilder) {
        var order = orderService.createNewOrder(request.toDTO());
        var location = uriComponentsBuilder.path("/orders/{id}")
                .buildAndExpand(order.getId()).toUri();
        return ResponseEntity.created(location).body(orderMapper.mapToDTO(order));
    }


}
