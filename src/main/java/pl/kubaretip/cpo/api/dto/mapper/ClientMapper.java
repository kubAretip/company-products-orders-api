package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pl.kubaretip.cpo.api.domain.Client;
import pl.kubaretip.cpo.api.dto.ClientDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {

    ClientDTO mapToDTO(Client entity);

    @Named("mapToClientDTOOnlyWithId")
    @Mapping(target = "id", source = "clientId")
    ClientDTO mapToClientDTOOnlyWithId(Long clientId);

}
