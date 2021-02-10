package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.kubaretip.cpo.api.domain.Address;
import pl.kubaretip.cpo.api.dto.AddressDTO;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address mapToEntity(AddressDTO dto);

    AddressDTO mapToDTO(Address entity);

    Set<Address> mapToEntityList(List<AddressDTO> list);

}
