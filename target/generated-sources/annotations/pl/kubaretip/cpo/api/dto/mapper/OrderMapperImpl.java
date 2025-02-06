package pl.kubaretip.cpo.api.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kubaretip.cpo.api.domain.Client;
import pl.kubaretip.cpo.api.domain.Order;
import pl.kubaretip.cpo.api.domain.OrderProduct;
import pl.kubaretip.cpo.api.domain.User;
import pl.kubaretip.cpo.api.dto.ClientDTO;
import pl.kubaretip.cpo.api.dto.OrderDTO;
import pl.kubaretip.cpo.api.dto.OrderProductDTO;
import pl.kubaretip.cpo.api.dto.UserDTO;
import pl.kubaretip.cpo.api.web.rest.request.AcceptOrderRequest;
import pl.kubaretip.cpo.api.web.rest.request.NewOrderRequest;
import pl.kubaretip.cpo.api.web.rest.request.RejectOrderRequest;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-06T10:37:16+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.21 (Eclipse Adoptium)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Autowired
    private OrderProductMapper orderProductMapper;
    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public OrderDTO mapToOrderDTO(Order entity) {
        if ( entity == null ) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setId( entity.getId() );
        orderDTO.setClient( clientMapper.mapToDTO( entity.getClient() ) );
        orderDTO.setMarketer( userToUserDTO( entity.getMarketer() ) );
        orderDTO.setSupervisor( userToUserDTO( entity.getSupervisor() ) );
        orderDTO.setDeliveryAddress( addressMapper.mapToDTO( entity.getDeliveryAddress() ) );
        orderDTO.setAdditionalInformation( entity.getAdditionalInformation() );
        orderDTO.setOrderProducts( orderProductSetToOrderProductDTOList( entity.getOrderProducts() ) );

        return orderDTO;
    }

    @Override
    public OrderDTO mapToOrderDTOWithoutClientAddresses(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setId( order.getId() );
        orderDTO.setClient( clientToClientDTO( order.getClient() ) );
        orderDTO.setMarketer( userToUserDTO( order.getMarketer() ) );
        orderDTO.setSupervisor( userToUserDTO( order.getSupervisor() ) );
        orderDTO.setDeliveryAddress( addressMapper.mapToDTO( order.getDeliveryAddress() ) );
        orderDTO.setAdditionalInformation( order.getAdditionalInformation() );
        orderDTO.setOrderProducts( orderProductSetToOrderProductDTOList( order.getOrderProducts() ) );

        return orderDTO;
    }

    @Override
    public List<OrderDTO> mapToOrderDTOList(List<Order> orderList) {
        if ( orderList == null ) {
            return null;
        }

        List<OrderDTO> list = new ArrayList<OrderDTO>( orderList.size() );
        for ( Order order : orderList ) {
            list.add( mapToOrderDTO( order ) );
        }

        return list;
    }

    @Override
    public OrderDTO mapRejectOrderRequestToOrderDTO(RejectOrderRequest request) {
        if ( request == null ) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setId( request.getId() );
        orderDTO.setAdditionalInformation( request.getAdditionalInformation() );

        return orderDTO;
    }

    @Override
    public OrderDTO mapAcceptOrderRequestToOrderDTO(AcceptOrderRequest request) {
        if ( request == null ) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setOrderProducts( orderProductMapper.mapOrderProductExecutorsListToOrderProductDTOList( request.getOrderExecutors() ) );
        orderDTO.setId( request.getId() );

        return orderDTO;
    }

    @Override
    public OrderDTO mapNewOrderRequestToOrderDTO(NewOrderRequest request) {
        if ( request == null ) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setClient( clientMapper.mapToClientDTOOnlyWithId( request.getClientId() ) );
        orderDTO.setDeliveryAddress( addressMapper.mapToAddressDTOOnlyWithId( request.getDeliveryAddressId() ) );
        orderDTO.setOrderProducts( orderProductMapper.mapNewOrderRequestProductListToOrderProductDTOList( request.getProducts() ) );

        return orderDTO;
    }

    protected UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setUsername( user.getUsername() );
        userDTO.setFirstName( user.getFirstName() );
        userDTO.setLastName( user.getLastName() );
        userDTO.setEmail( user.getEmail() );
        userDTO.setPhoneNumber( user.getPhoneNumber() );

        return userDTO;
    }

    protected List<OrderProductDTO> orderProductSetToOrderProductDTOList(Set<OrderProduct> set) {
        if ( set == null ) {
            return null;
        }

        List<OrderProductDTO> list = new ArrayList<OrderProductDTO>( set.size() );
        for ( OrderProduct orderProduct : set ) {
            list.add( orderProductMapper.orderProductToOrderProductDTO( orderProduct ) );
        }

        return list;
    }

    protected ClientDTO clientToClientDTO(Client client) {
        if ( client == null ) {
            return null;
        }

        ClientDTO clientDTO = new ClientDTO();

        clientDTO.setId( client.getId() );
        clientDTO.setCompanyName( client.getCompanyName() );
        clientDTO.setFirstName( client.getFirstName() );
        clientDTO.setLastName( client.getLastName() );
        clientDTO.setEmail( client.getEmail() );
        clientDTO.setPhoneNumber( client.getPhoneNumber() );

        return clientDTO;
    }
}
