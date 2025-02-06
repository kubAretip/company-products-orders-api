package pl.kubaretip.cpo.api.dto.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pl.kubaretip.cpo.api.domain.User;
import pl.kubaretip.cpo.api.dto.UserDTO;
import pl.kubaretip.cpo.api.web.rest.request.EditUserRequest;
import pl.kubaretip.cpo.api.web.rest.request.UpdateUserRequest;
import pl.kubaretip.cpo.api.web.rest.request.UserRequest;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-06T10:37:16+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.21 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO mapToDTO(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( entity.getId() );
        userDTO.setUsername( entity.getUsername() );
        userDTO.setFirstName( entity.getFirstName() );
        userDTO.setLastName( entity.getLastName() );
        userDTO.setEmail( entity.getEmail() );
        userDTO.setPhoneNumber( entity.getPhoneNumber() );

        return userDTO;
    }

    @Override
    public UserDTO mapUpdateUserRequestToUserDTO(UpdateUserRequest request) {
        if ( request == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( request.getId() );
        userDTO.setFirstName( request.getFirstName() );
        userDTO.setLastName( request.getLastName() );
        userDTO.setEmail( request.getEmail() );
        userDTO.setPhoneNumber( request.getPhoneNumber() );

        return userDTO;
    }

    @Override
    public UserDTO mapEditUserRequestToUserDTO(EditUserRequest request) {
        if ( request == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setPhoneNumber( request.getPhoneNumber() );

        return userDTO;
    }

    @Override
    public UserDTO mapUserRequestToUserDTO(UserRequest request) {
        if ( request == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setFirstName( request.getFirstName() );
        userDTO.setLastName( request.getLastName() );
        userDTO.setEmail( request.getEmail() );

        return userDTO;
    }

    @Override
    public UserDTO mapToUserDTOOnlyWithId(Long id) {
        if ( id == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( id );

        return userDTO;
    }
}
