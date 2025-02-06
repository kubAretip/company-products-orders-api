package pl.kubaretip.cpo.api.dto.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pl.kubaretip.cpo.api.domain.Unit;
import pl.kubaretip.cpo.api.dto.UnitDTO;
import pl.kubaretip.cpo.api.web.rest.request.EditUnitRequest;
import pl.kubaretip.cpo.api.web.rest.request.NewUnitRequest;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-06T10:37:16+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.21 (Eclipse Adoptium)"
)
@Component
public class UnitMapperImpl implements UnitMapper {

    @Override
    public UnitDTO mapToDTO(Unit entity) {
        if ( entity == null ) {
            return null;
        }

        UnitDTO unitDTO = new UnitDTO();

        unitDTO.setId( entity.getId() );
        unitDTO.setName( entity.getName() );
        unitDTO.setSymbol( entity.getSymbol() );

        return unitDTO;
    }

    @Override
    public UnitDTO mapNewUnitRequestToUnitDTO(NewUnitRequest request) {
        if ( request == null ) {
            return null;
        }

        UnitDTO unitDTO = new UnitDTO();

        unitDTO.setName( request.getName() );
        unitDTO.setSymbol( request.getSymbol() );

        return unitDTO;
    }

    @Override
    public UnitDTO mapEditUnitRequestToUnitDTO(EditUnitRequest request) {
        if ( request == null ) {
            return null;
        }

        UnitDTO unitDTO = new UnitDTO();

        unitDTO.setId( request.getId() );
        unitDTO.setName( request.getName() );
        unitDTO.setSymbol( request.getSymbol() );

        return unitDTO;
    }

    @Override
    public UnitDTO mapToUnitDTOOnlyWithId(Long unitId) {
        if ( unitId == null ) {
            return null;
        }

        UnitDTO unitDTO = new UnitDTO();

        unitDTO.setId( unitId );

        return unitDTO;
    }
}
