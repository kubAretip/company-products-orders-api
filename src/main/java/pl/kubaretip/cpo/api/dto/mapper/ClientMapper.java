package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pl.kubaretip.cpo.api.domain.Client;
import pl.kubaretip.cpo.api.dto.ClientDTO;
import pl.kubaretip.cpo.api.web.rest.request.ClientRequest;
import pl.kubaretip.cpo.api.web.rest.request.NewClientRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {AddressMapper.class})
public interface ClientMapper {

    ClientDTO mapToDTO(Client entity);

    @Named("mapToClientDTOOnlyWithId")
    @Mapping(target = "id", source = "clientId")
    ClientDTO mapToClientDTOOnlyWithId(Long clientId);

    @Mapping(target = "addresses", source = "address", qualifiedByName = "mapAddressRequestToAddressDTOList")
    ClientDTO mapNewClientRequestToClientDTO(NewClientRequest request);

    ClientDTO mapClientRequestToClientDTO(ClientRequest request);

}
