package pl.kubaretip.cpo.api.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kubaretip.cpo.api.domain.Address;
import pl.kubaretip.cpo.api.domain.Client;
import pl.kubaretip.cpo.api.dto.AddressDTO;
import pl.kubaretip.cpo.api.dto.ClientDTO;
import pl.kubaretip.cpo.api.web.rest.request.ClientRequest;
import pl.kubaretip.cpo.api.web.rest.request.NewClientRequest;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-06T10:37:16+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.21 (Eclipse Adoptium)"
)
@Component
public class ClientMapperImpl implements ClientMapper {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public ClientDTO mapToDTO(Client entity) {
        if ( entity == null ) {
            return null;
        }

        ClientDTO clientDTO = new ClientDTO();

        clientDTO.setId( entity.getId() );
        clientDTO.setCompanyName( entity.getCompanyName() );
        clientDTO.setFirstName( entity.getFirstName() );
        clientDTO.setLastName( entity.getLastName() );
        clientDTO.setEmail( entity.getEmail() );
        clientDTO.setPhoneNumber( entity.getPhoneNumber() );
        clientDTO.setAddresses( addressSetToAddressDTOList( entity.getAddresses() ) );

        return clientDTO;
    }

    @Override
    public ClientDTO mapToClientDTOOnlyWithId(Long clientId) {
        if ( clientId == null ) {
            return null;
        }

        ClientDTO clientDTO = new ClientDTO();

        clientDTO.setId( clientId );

        return clientDTO;
    }

    @Override
    public ClientDTO mapNewClientRequestToClientDTO(NewClientRequest request) {
        if ( request == null ) {
            return null;
        }

        ClientDTO clientDTO = new ClientDTO();

        clientDTO.setAddresses( addressMapper.mapAddressRequestToAddressDTOList( request.getAddress() ) );
        clientDTO.setCompanyName( request.getCompanyName() );
        clientDTO.setFirstName( request.getFirstName() );
        clientDTO.setLastName( request.getLastName() );
        clientDTO.setEmail( request.getEmail() );
        clientDTO.setPhoneNumber( request.getPhoneNumber() );

        return clientDTO;
    }

    @Override
    public ClientDTO mapClientRequestToClientDTO(ClientRequest request) {
        if ( request == null ) {
            return null;
        }

        ClientDTO clientDTO = new ClientDTO();

        clientDTO.setCompanyName( request.getCompanyName() );
        clientDTO.setFirstName( request.getFirstName() );
        clientDTO.setLastName( request.getLastName() );
        clientDTO.setEmail( request.getEmail() );
        clientDTO.setPhoneNumber( request.getPhoneNumber() );

        return clientDTO;
    }

    protected List<AddressDTO> addressSetToAddressDTOList(Set<Address> set) {
        if ( set == null ) {
            return null;
        }

        List<AddressDTO> list = new ArrayList<AddressDTO>( set.size() );
        for ( Address address : set ) {
            list.add( addressMapper.mapToDTO( address ) );
        }

        return list;
    }
}
