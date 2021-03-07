package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.*;
import pl.kubaretip.cpo.api.domain.OrderProduct;
import pl.kubaretip.cpo.api.dto.OrderProductDTO;
import pl.kubaretip.cpo.api.web.rest.request.AcceptOrderRequest;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class})
public interface OrderProductMapper {

    OrderProductDTO orderProductToOrderProductDTO(OrderProduct entity);

    @Named("mapOrderProductExecutorToOrderProduct")
    @Mapping(target = "id", source = "orderProductId")
    @Mapping(target = "executor", source = "executorId", qualifiedByName = "mapToUserDTOOnlyWithId")
    OrderProductDTO mapOrderProductExecutorToOrderProduct(AcceptOrderRequest.OrderProductExecutors orderProductExecutors);

    @Named("acceptOrderProductsToOrderProductsDTO")
    @IterableMapping(qualifiedByName = "mapOrderProductExecutorToOrderProduct")
    List<OrderProductDTO> mapOrderProductExecutorsListToOrderProductDTOList(List<AcceptOrderRequest.OrderProductExecutors> executors);


}
