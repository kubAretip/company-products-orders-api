package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pl.kubaretip.cpo.api.domain.Address;
import pl.kubaretip.cpo.api.dto.AddressDTO;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {

    Address mapToEntity(AddressDTO dto);

    AddressDTO mapToDTO(Address entity);

    Set<Address> mapToEntityList(List<AddressDTO> list);

    @Named("mapToAddressDTOOnlyWithId")
    @Mapping(target = "id", source = "deliveryAddressId")
    AddressDTO mapToAddressDTOOnlyWithId(Long deliveryAddressId);

}
