package pl.kubaretip.cpo.api.service.impl;

import org.springframework.stereotype.Service;
import pl.kubaretip.cpo.api.constants.StatusConstants;
import pl.kubaretip.cpo.api.domain.Order;
import pl.kubaretip.cpo.api.domain.OrderProduct;
import pl.kubaretip.cpo.api.dto.OrderDTO;
import pl.kubaretip.cpo.api.exception.NotFoundException;
import pl.kubaretip.cpo.api.exception.UserResourceException;
import pl.kubaretip.cpo.api.repository.OrderRepository;
import pl.kubaretip.cpo.api.service.*;
import pl.kubaretip.cpo.api.util.SecurityUtils;
import pl.kubaretip.cpo.api.util.Translator;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final OrderStatusService orderStatusService;
    private final ClientService clientService;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final Translator translator;

    public OrderServiceImpl(UserService userService,
                            ClientService clientService,
                            OrderStatusService orderStatusService,
                            ProductService productService,
                            OrderRepository orderRepository,
                            Translator translator) {
        this.userService = userService;
        this.clientService = clientService;
        this.orderStatusService = orderStatusService;
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.translator = translator;
    }

    @Override
    public Order createNewOrder(OrderDTO orderDTO) {

        var newOrder = new Order();

        var productsIds = orderDTO.getOrderProducts()
                .stream()
                .map(orderProduct -> orderProduct.getProduct().getId())
                .collect(Collectors.toList());

        if (!productService.existsProductsWithIds(productsIds)) {
            throw new NotFoundException("", "Order contains not existed product");
        }

        var createdBy = SecurityUtils.getCurrentUserLogin()
                .map(userService::findByUsername)
                .orElseThrow(() -> new UserResourceException("Current user not found"));

        var client = clientService.findClientById(orderDTO.getClient().getId());

        var newOrderStatus = orderStatusService.getOrderStatus(StatusConstants.CREATED);
        newOrderStatus.setOrder(newOrder);

        var orderProducts = orderDTO.getOrderProducts()
                .stream()
                .map(orderProductDTO -> {
                    var orderProduct = new OrderProduct();
                    orderProduct.setOrder(newOrder);
                    orderProduct.setProduct(productService.findProductById(orderProductDTO.getProduct().getId()));
                    orderProduct.setQuantity(orderProductDTO.getQuantity());
                    return orderProduct;
                })
                .collect(Collectors.toSet());

        var deliveryAddress = client.getAddresses()
                .stream()
                .filter(address -> address.getId().equals(orderDTO.getDeliveryAddress().getId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(translator.translate("common.notFound.title"),
                        "Client address with id " + orderDTO.getDeliveryAddress().getId() + " not exists"));

        newOrder.setDeliveryAddress(deliveryAddress);
        newOrder.setCreatedBy(createdBy);
        newOrder.setClient(client);
        newOrder.setOrderProducts(orderProducts);
        newOrder.setOrderStatus(Collections.singleton(newOrderStatus));

        return orderRepository.save(newOrder);
    }


}
