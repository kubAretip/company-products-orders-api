package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pl.kubaretip.cpo.api.domain.User;
import pl.kubaretip.cpo.api.dto.UserDTO;
import pl.kubaretip.cpo.api.web.rest.request.EditUserRequest;
import pl.kubaretip.cpo.api.web.rest.request.UpdateUserRequest;
import pl.kubaretip.cpo.api.web.rest.request.UserRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDTO mapToDTO(User entity);

    UserDTO mapUpdateUserRequestToUserDTO(UpdateUserRequest request);

    UserDTO mapEditUserRequestToUserDTO(EditUserRequest request);

    UserDTO mapUserRequestToUserDTO(UserRequest request);

    @Named("mapToUserDTOOnlyWithId")
    @Mapping(target = "id", source = "id")
    UserDTO mapToUserDTOOnlyWithId(Long id);
}
