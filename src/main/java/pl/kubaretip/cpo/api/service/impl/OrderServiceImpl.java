package pl.kubaretip.cpo.api.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pl.kubaretip.cpo.api.constants.AuthoritiesConstants;
import pl.kubaretip.cpo.api.constants.StatusConstants;
import pl.kubaretip.cpo.api.domain.*;
import pl.kubaretip.cpo.api.dto.OrderDTO;
import pl.kubaretip.cpo.api.exception.InvalidDataException;
import pl.kubaretip.cpo.api.exception.NotFoundException;
import pl.kubaretip.cpo.api.exception.OrderStatusException;
import pl.kubaretip.cpo.api.exception.UserResourceException;
import pl.kubaretip.cpo.api.repository.OrderRepository;
import pl.kubaretip.cpo.api.service.*;
import pl.kubaretip.cpo.api.util.SecurityUtils;
import pl.kubaretip.cpo.api.util.Translator;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

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
            throw new NotFoundException(translator.translate("common.notFound.title"),
                    translator.translate("order.incorrect.productsList"));
        }

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
                .collect(toSet());

        var deliveryAddress = client.getAddresses()
                .stream()
                .filter(address -> address.getId().equals(orderDTO.getDeliveryAddress().getId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(translator.translate("common.notFound.title"),
                        translator.translate("client.notFound.address")));

        newOrder.setDeliveryAddress(deliveryAddress);
        newOrder.setMarketer(getCurrentUser());
        newOrder.setClient(client);
        newOrder.setOrderProducts(orderProducts);
        newOrder.setOrderStatus(Collections.singleton(newOrderStatus));

        return orderRepository.save(newOrder);
    }

    @Override
    public void acceptOrder(OrderDTO orderDTO) {
        var order = getOrderById(orderDTO.getId());
        throwExceptionIfAlreadyAcceptedOrRejected(order);

        order.setSupervisor(getCurrentUser());

        order.getOrderProducts()
                .forEach(orderProduct -> {
                    var orderProductDTO = orderDTO.getOrderProducts()
                            .stream()
                            .filter(opDTO -> orderProduct.getId().equals(opDTO.getId()))
                            .findFirst()
                            .orElseThrow(() -> new InvalidDataException("common.badRequest.title",
                                    translator.translate("order.missingOrderProductExecutor",
                                            new Object[]{orderProduct.getId()}))
                            );

                    orderProduct.setExecutor(userService.findUserByIdAndAuthority(orderProductDTO.getExecutor().getId(),
                            AuthoritiesConstants.ROLE_EXECUTOR));
                });

        var acceptedOrderStatus = orderStatusService.getOrderStatus(StatusConstants.ACCEPTED);
        acceptedOrderStatus.setOrder(order);
        order.getOrderStatus().add(acceptedOrderStatus);

        orderRepository.flush();
        orderRepository.save(order);
    }

    @Override
    public void rejectOrder(OrderDTO orderDTO) {
        var order = getOrderById(orderDTO.getId());
        throwExceptionIfAlreadyAcceptedOrRejected(order);
        order.setSupervisor(getCurrentUser());

        if (StringUtils.isNotEmpty(orderDTO.getAdditionalInformation())) {
            order.setAdditionalInformation(orderDTO.getAdditionalInformation());
        }

        var rejectedOrderStatus = orderStatusService.getOrderStatus(StatusConstants.REJECTED);
        rejectedOrderStatus.setOrder(order);
        order.getOrderStatus().add(rejectedOrderStatus);
        orderRepository.save(order);
    }

    User getCurrentUser() {
        return SecurityUtils.getCurrentUserLogin()
                .map(userService::findByUsername)
                .orElseThrow(this::userResourceException);
    }

    void throwExceptionIfAlreadyAcceptedOrRejected(Order order) {
        if (isOrderAlreadyAccepted(order)) {
            throw new OrderStatusException(translator.translate("common.badRequest.title"),
                    translator.translate("order.already.accepted"));
        }
        if (isOrderAlreadyRejected(order)) {
            throw new OrderStatusException(translator.translate("common.badRequest.title"),
                    translator.translate("order.already.rejected"));
        }
    }

    Order getOrderById(long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(translator.translate("common.notFound.title"),
                        translator.translate("order.notFound", new Object[]{orderId})));
    }

    boolean isOrderAlreadyAccepted(Order order) {
        return getOrderStatuses(order).stream()
                .anyMatch(status -> status.getName().equals(StatusConstants.ACCEPTED.name()));
    }

    boolean isOrderAlreadyRejected(Order order) {
        return getOrderStatuses(order).stream()
                .anyMatch(status -> status.getName().equals(StatusConstants.ACCEPTED.name()));
    }

    List<Status> getOrderStatuses(Order order) {
        return order.getOrderStatus()
                .stream()
                .map(OrderStatus::getStatus)
                .collect(Collectors.toList());
    }

    private UserResourceException userResourceException() {
        return new UserResourceException(translator.translate("user.notFound.userResource"));
    }


}
