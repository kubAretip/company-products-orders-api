package pl.kubaretip.cpo.api.service;

import pl.kubaretip.cpo.api.dto.UserDTO;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    UserDTO activateUser(String username, String password, String activationKey);

    void assignUserToNewAuthority(Long userId, String role);

    void removeUserAuthority(Long userId, String role);
}
