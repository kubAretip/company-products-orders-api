package pl.kubaretip.cpo.api.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kubaretip.cpo.api.domain.OrderProduct;
import pl.kubaretip.cpo.api.dto.OrderProductDTO;
import pl.kubaretip.cpo.api.web.rest.request.AcceptOrderRequest.OrderProductExecutors;
import pl.kubaretip.cpo.api.web.rest.request.NewOrderRequest.ProductInOrder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-06T10:37:16+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.21 (Eclipse Adoptium)"
)
@Component
public class OrderProductMapperImpl implements OrderProductMapper {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public OrderProductDTO orderProductToOrderProductDTO(OrderProduct entity) {
        if ( entity == null ) {
            return null;
        }

        OrderProductDTO orderProductDTO = new OrderProductDTO();

        orderProductDTO.setId( entity.getId() );
        orderProductDTO.setQuantity( entity.getQuantity() );
        orderProductDTO.setProduct( productMapper.mapToDTO( entity.getProduct() ) );
        orderProductDTO.setExecutor( userMapper.mapToDTO( entity.getExecutor() ) );

        return orderProductDTO;
    }

    @Override
    public OrderProductDTO mapOrderProductExecutorToOrderProduct(OrderProductExecutors orderProductExecutors) {
        if ( orderProductExecutors == null ) {
            return null;
        }

        OrderProductDTO orderProductDTO = new OrderProductDTO();

        orderProductDTO.setId( orderProductExecutors.getOrderProductId() );
        orderProductDTO.setExecutor( userMapper.mapToUserDTOOnlyWithId( orderProductExecutors.getExecutorId() ) );

        return orderProductDTO;
    }

    @Override
    public List<OrderProductDTO> mapOrderProductExecutorsListToOrderProductDTOList(List<OrderProductExecutors> executors) {
        if ( executors == null ) {
            return null;
        }

        List<OrderProductDTO> list = new ArrayList<OrderProductDTO>( executors.size() );
        for ( OrderProductExecutors orderProductExecutors : executors ) {
            list.add( mapOrderProductExecutorToOrderProduct( orderProductExecutors ) );
        }

        return list;
    }

    @Override
    public OrderProductDTO mapNewOrderRequestProductToOrderProductDTO(ProductInOrder newOrderRequestProducts) {
        if ( newOrderRequestProducts == null ) {
            return null;
        }

        OrderProductDTO orderProductDTO = new OrderProductDTO();

        orderProductDTO.setProduct( productMapper.mapToProductDTOOnlyWithId( newOrderRequestProducts.getProductId() ) );
        orderProductDTO.setQuantity( newOrderRequestProducts.getQuantity() );

        return orderProductDTO;
    }

    @Override
    public List<OrderProductDTO> mapNewOrderRequestProductListToOrderProductDTOList(List<ProductInOrder> productInOrders) {
        if ( productInOrders == null ) {
            return null;
        }

        List<OrderProductDTO> list = new ArrayList<OrderProductDTO>( productInOrders.size() );
        for ( ProductInOrder productInOrder : productInOrders ) {
            list.add( mapNewOrderRequestProductToOrderProductDTO( productInOrder ) );
        }

        return list;
    }

    @Override
    public List<OrderProductDTO> mapOrderProductListToOrderProductDTOList(List<OrderProduct> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<OrderProductDTO> list = new ArrayList<OrderProductDTO>( entityList.size() );
        for ( OrderProduct orderProduct : entityList ) {
            list.add( mapToOrderProductDTOWithoutExecutor( orderProduct ) );
        }

        return list;
    }

    @Override
    public OrderProductDTO mapToOrderProductDTOWithoutExecutor(OrderProduct orderProduct) {
        if ( orderProduct == null ) {
            return null;
        }

        OrderProductDTO orderProductDTO = new OrderProductDTO();

        orderProductDTO.setId( orderProduct.getId() );
        orderProductDTO.setQuantity( orderProduct.getQuantity() );
        orderProductDTO.setProduct( productMapper.mapToDTO( orderProduct.getProduct() ) );

        return orderProductDTO;
    }
}
