package pl.kubaretip.cpo.api.dto.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pl.kubaretip.cpo.api.domain.Address;
import pl.kubaretip.cpo.api.dto.AddressDTO;
import pl.kubaretip.cpo.api.web.rest.request.UpdateAddressRequest;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-06T10:37:16+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.21 (Eclipse Adoptium)"
)
@Component
public class AddressMapperImpl implements AddressMapper {

    @Override
    public Address mapToEntity(AddressDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Address address = new Address();

        address.setId( dto.getId() );
        address.setCountry( dto.getCountry() );
        address.setStreet( dto.getStreet() );
        address.setZipCode( dto.getZipCode() );
        address.setApartment( dto.getApartment() );
        address.setBuilding( dto.getBuilding() );
        address.setCity( dto.getCity() );

        return address;
    }

    @Override
    public AddressDTO mapToDTO(Address entity) {
        if ( entity == null ) {
            return null;
        }

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setId( entity.getId() );
        addressDTO.setCountry( entity.getCountry() );
        addressDTO.setStreet( entity.getStreet() );
        addressDTO.setZipCode( entity.getZipCode() );
        addressDTO.setApartment( entity.getApartment() );
        addressDTO.setBuilding( entity.getBuilding() );
        addressDTO.setCity( entity.getCity() );

        return addressDTO;
    }

    @Override
    public Set<Address> mapToEntityList(List<AddressDTO> list) {
        if ( list == null ) {
            return null;
        }

        Set<Address> set = new HashSet<Address>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( AddressDTO addressDTO : list ) {
            set.add( mapToEntity( addressDTO ) );
        }

        return set;
    }

    @Override
    public AddressDTO mapToAddressDTOOnlyWithId(Long deliveryAddressId) {
        if ( deliveryAddressId == null ) {
            return null;
        }

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setId( deliveryAddressId );

        return addressDTO;
    }

    @Override
    public AddressDTO mapUpdateAddressRequestToAddressDTO(UpdateAddressRequest request) {
        if ( request == null ) {
            return null;
        }

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setId( request.getId() );
        addressDTO.setCountry( request.getCountry() );
        addressDTO.setStreet( request.getStreet() );
        addressDTO.setZipCode( request.getZipCode() );
        addressDTO.setApartment( request.getApartment() );
        addressDTO.setBuilding( request.getBuilding() );
        addressDTO.setCity( request.getCity() );

        return addressDTO;
    }
}
