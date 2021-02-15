package pl.kubaretip.cpo.api.service;

import com.itextpdf.text.DocumentException;
import pl.kubaretip.cpo.api.domain.User;
import pl.kubaretip.cpo.api.dto.UserDTO;

import java.io.FileNotFoundException;

public interface UserService {
    User createUser(UserDTO userDTO) throws FileNotFoundException, DocumentException;

    User activateUser(String username, String password, String activationKey);

    void assignUserToNewAuthority(Long userId, String role);

    void removeUserAuthority(Long userId, String role);

    User findByUsername(String username);
}
