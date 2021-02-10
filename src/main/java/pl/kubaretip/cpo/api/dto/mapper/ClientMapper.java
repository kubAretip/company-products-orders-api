package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.kubaretip.cpo.api.domain.Client;
import pl.kubaretip.cpo.api.dto.ClientDTO;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDTO mapToDTO(Client entity);

}
