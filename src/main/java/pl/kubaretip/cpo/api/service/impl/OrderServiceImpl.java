package pl.kubaretip.cpo.api.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.kubaretip.cpo.api.constants.AuthoritiesConstants;
import pl.kubaretip.cpo.api.constants.StatusConstants;
import pl.kubaretip.cpo.api.domain.Order;
import pl.kubaretip.cpo.api.domain.OrderProduct;
import pl.kubaretip.cpo.api.domain.OrderStatus;
import pl.kubaretip.cpo.api.domain.Status;
import pl.kubaretip.cpo.api.dto.OrderDTO;
import pl.kubaretip.cpo.api.exception.InvalidDataException;
import pl.kubaretip.cpo.api.exception.NotFoundException;
import pl.kubaretip.cpo.api.exception.OrderStatusException;
import pl.kubaretip.cpo.api.repository.OrderRepository;
import pl.kubaretip.cpo.api.service.*;

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

    public OrderServiceImpl(UserService userService,
                            ClientService clientService,
                            OrderStatusService orderStatusService,
                            ProductService productService,
                            OrderRepository orderRepository) {
        this.userService = userService;
        this.clientService = clientService;
        this.orderStatusService = orderStatusService;
        this.productService = productService;
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createNewOrder(OrderDTO orderDTO, long marketerId) {

        var newOrder = new Order();

        var productsIds = orderDTO.getOrderProducts()
                .stream()
                .map(orderProduct -> orderProduct.getProduct().getId())
                .collect(Collectors.toList());

        if (!productService.existsProductsWithIds(productsIds)) {
            throw new NotFoundException("exception.order.incorrect.productsList");
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
                .filter(address -> address.getId().equals(orderDTO.getDeliveryAddress().getId()) && !address.isDeleted())
                .findFirst()
                .orElseThrow(() -> new NotFoundException("exception.client.notFound.address"));

        newOrder.setDeliveryAddress(deliveryAddress);
        newOrder.setMarketer(userService.findUserByIdAndAuthority(marketerId, AuthoritiesConstants.ROLE_MARKETER));
        newOrder.setClient(client);
        newOrder.setOrderProducts(orderProducts);
        newOrder.setOrderStatus(Collections.singleton(newOrderStatus));

        return orderRepository.save(newOrder);
    }

    @Override
    public void acceptOrder(OrderDTO orderDTO, long supervisorId) {
        var order = getOrderById(orderDTO.getId());

        throwExceptionIfAlreadyAcceptedOrRejected(order);

        order.setSupervisor(userService.findUserByIdAndAuthority(supervisorId, AuthoritiesConstants.ROLE_SUPERVISOR));

        order.getOrderProducts()
                .forEach(orderProduct -> {
                    var orderProductDTO = orderDTO.getOrderProducts()
                            .stream()
                            .filter(opDTO -> orderProduct.getId().equals(opDTO.getId()))
                            .findFirst()
                            .orElseThrow(() -> new InvalidDataException("exception.order.missingOrderProductExecutor",
                                    new Object[]{orderProduct.getId()}));

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
    public void rejectOrder(OrderDTO orderDTO, long supervisorId) {
        var order = getOrderById(orderDTO.getId());
        throwExceptionIfAlreadyAcceptedOrRejected(order);
        order.setSupervisor(userService.findUserByIdAndAuthority(supervisorId, AuthoritiesConstants.ROLE_SUPERVISOR));

        if (StringUtils.isNotEmpty(orderDTO.getAdditionalInformation())) {
            order.setAdditionalInformation(orderDTO.getAdditionalInformation());
        }

        var rejectedOrderStatus = orderStatusService.getOrderStatus(StatusConstants.REJECTED);
        rejectedOrderStatus.setOrder(order);
        order.getOrderStatus().add(rejectedOrderStatus);
        orderRepository.flush();
        orderRepository.save(order);
    }

    void throwExceptionIfAlreadyAcceptedOrRejected(Order order) {
        if (isOrderAlreadyAccepted(order)) {
            throw new OrderStatusException("exception.order.already.accepted");
        }
        if (isOrderAlreadyRejected(order)) {
            throw new OrderStatusException("exception.order.already.rejected");
        }
    }

    @Override
    public Order getOrderById(long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("exception.order.notFound", new Object[]{orderId}));
    }

    boolean isOrderAlreadyAccepted(Order order) {
        return getOrderStatuses(order).stream()
                .anyMatch(status -> status.getName().equals(StatusConstants.ACCEPTED.name()));
    }

    boolean isOrderAlreadyRejected(Order order) {
        return getOrderStatuses(order).stream()
                .anyMatch(status -> status.getName().equals(StatusConstants.REJECTED.name()));
    }

    List<Status> getOrderStatuses(Order order) {
        return order.getOrderStatus()
                .stream()
                .map(OrderStatus::getStatus)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getOrdersWithPendingSupervisorAcceptance() {
        return orderRepository.findBySupervisorIsNull();
    }

    @Override
    public List<Order> getOrders(int from, int to) {
        return orderRepository.findAll(PageRequest.of(from, to)).toList();
    }


}
