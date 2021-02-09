package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.kubaretip.cpo.api.domain.User;
import pl.kubaretip.cpo.api.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO mapToDTO(User entity);

}
