package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kubaretip.cpo.api.domain.User;
import pl.kubaretip.cpo.api.dto.UserDTO;
import pl.kubaretip.cpo.api.web.rest.request.EditUserRequest;
import pl.kubaretip.cpo.api.web.rest.request.UpdateUserRequest;
import pl.kubaretip.cpo.api.web.rest.request.UserRequest;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO mapToDTO(User entity);

    @Mapping(target = "username", ignore = true)
    UserDTO mapUpdateUserRequestToUserDTO(UpdateUserRequest request);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "email", ignore = true)
    UserDTO mapEditUserRequestToUserDTO(EditUserRequest request);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "id", ignore = true)
    UserDTO mapUserRequestToUserDTO(UserRequest request);

}
