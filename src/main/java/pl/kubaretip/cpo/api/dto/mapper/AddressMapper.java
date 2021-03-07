package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.*;
import pl.kubaretip.cpo.api.domain.Address;
import pl.kubaretip.cpo.api.dto.AddressDTO;
import pl.kubaretip.cpo.api.web.rest.request.AddressRequest;
import pl.kubaretip.cpo.api.web.rest.request.UpdateAddressRequest;

import java.util.Collections;
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

    AddressDTO mapUpdateAddressRequestToAddressDTO(UpdateAddressRequest request);

    @Named("mapAddressRequestToAddressDTOList")
    default List<AddressDTO> mapAddressRequestToAddressDTOList(AddressRequest request) {

        if (request == null)
            return null;

        var addressDTO = new AddressDTO();
        addressDTO.setApartment(request.getApartment());
        addressDTO.setBuilding(request.getBuilding());
        addressDTO.setCity(request.getCity());
        addressDTO.setCountry(request.getCountry());
        addressDTO.setStreet(request.getStreet());
        addressDTO.setZipCode(request.getZipCode());

        return Collections.singletonList(addressDTO);
    }

}
