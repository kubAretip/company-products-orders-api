package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kubaretip.cpo.api.domain.OrderStatus;
import pl.kubaretip.cpo.api.dto.OrderStatusDTO;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface OrderStatusMapper {

    @Mapping(target = "statusDate", expression = "java(convertDate(entity.getStatusDate()))")
    OrderStatusDTO mapToDTO(OrderStatus entity);

    default String convertDate(OffsetDateTime date) {
        return date.format(DateTimeFormatter.BASIC_ISO_DATE);
    }

}
