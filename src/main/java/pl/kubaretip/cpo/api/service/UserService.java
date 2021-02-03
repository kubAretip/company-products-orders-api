package pl.kubaretip.cpo.api.service;

import pl.kubaretip.cpo.api.dto.UserDTO;

import javax.validation.Valid;

public interface UserService {
    UserDTO createUser(@Valid UserDTO userDTO);
}
