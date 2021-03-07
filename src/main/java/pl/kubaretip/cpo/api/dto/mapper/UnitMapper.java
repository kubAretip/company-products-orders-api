package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pl.kubaretip.cpo.api.domain.Unit;
import pl.kubaretip.cpo.api.dto.UnitDTO;
import pl.kubaretip.cpo.api.web.rest.request.EditUnitRequest;
import pl.kubaretip.cpo.api.web.rest.request.NewUnitRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UnitMapper {

    UnitDTO mapToDTO(Unit entity);

    UnitDTO mapNewUnitRequestToUnitDTO(NewUnitRequest request);

    UnitDTO mapEditUnitRequestToUnitDTO(EditUnitRequest request);

    @Named("mapToUnitDTOOnlyWithId")
    @Mapping(target = "id", source = "unitId")
    UnitDTO mapToUnitDTOOnlyWithId(Long unitId);
}
