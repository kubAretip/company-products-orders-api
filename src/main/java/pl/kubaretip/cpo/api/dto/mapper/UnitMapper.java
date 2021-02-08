package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.kubaretip.cpo.api.domain.Unit;
import pl.kubaretip.cpo.api.dto.UnitDTO;

@Mapper(componentModel = "spring")
public interface UnitMapper {

    UnitDTO mapToDTO(Unit entity);

    Unit mapToEntity(UnitDTO dto);
}
