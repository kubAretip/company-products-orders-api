package pl.kubaretip.cpo.api.dto.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pl.kubaretip.cpo.api.domain.OrderStatus;
import pl.kubaretip.cpo.api.domain.Status;
import pl.kubaretip.cpo.api.dto.OrderStatusDTO;
import pl.kubaretip.cpo.api.dto.StatusDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-06T10:37:16+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.21 (Eclipse Adoptium)"
)
@Component
public class OrderStatusMapperImpl implements OrderStatusMapper {

    @Override
    public OrderStatusDTO mapToDTO(OrderStatus entity) {
        if ( entity == null ) {
            return null;
        }

        OrderStatusDTO orderStatusDTO = new OrderStatusDTO();

        orderStatusDTO.setStatus( statusToStatusDTO( entity.getStatus() ) );

        orderStatusDTO.setStatusDate( convertDate(entity.getStatusDate()) );

        return orderStatusDTO;
    }

    protected StatusDTO statusToStatusDTO(Status status) {
        if ( status == null ) {
            return null;
        }

        StatusDTO statusDTO = new StatusDTO();

        statusDTO.setTitle( status.getTitle() );
        statusDTO.setDescription( status.getDescription() );

        return statusDTO;
    }
}
