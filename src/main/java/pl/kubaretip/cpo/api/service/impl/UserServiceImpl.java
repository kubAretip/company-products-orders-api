package pl.kubaretip.cpo.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kubaretip.cpo.api.domain.User;
import pl.kubaretip.cpo.api.dto.UserDTO;
import pl.kubaretip.cpo.api.dto.mapper.UserMapper;
import pl.kubaretip.cpo.api.exception.AlreadyExistsException;
import pl.kubaretip.cpo.api.exception.AuthorityNotExistsException;
import pl.kubaretip.cpo.api.exception.InvalidDataException;
import pl.kubaretip.cpo.api.exception.NotFoundException;
import pl.kubaretip.cpo.api.repository.AuthorityRepository;
import pl.kubaretip.cpo.api.repository.UserRepository;
import pl.kubaretip.cpo.api.security.AuthoritiesConstants;
import pl.kubaretip.cpo.api.service.UserService;

@Slf4j
@Service
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthorityRepository authorityRepository;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper,
                           AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        log.debug("Creating new user");
        if (userRepository.existsByEmailIgnoreCase(userDTO.getEmail())) {
            throw new AlreadyExistsException("Email is already use", "Email " + userDTO.getEmail()
                    + " is already in use");
        }

        var user = new User();
        var activationKey = String.valueOf(RandomUtils.nextLong(100_000_000, 999_999_999));

        user.setFirstName(StringUtils.capitalize(userDTO.getFirstName().toLowerCase()));
        user.setLastName(StringUtils.capitalize(userDTO.getLastName().toLowerCase()));
        user.setEmail(userDTO.getEmail().toLowerCase());
        user.setActivationKey(passwordEncoder.encode(activationKey));

        // generate username
        userRepository.findLatestUsernameForFirstNameAndLastName(user.getFirstName(), user.getLastName())
                .ifPresentOrElse(u -> {
                    var lastUserNameChar = u.getUsername().charAt(u.getUsername().length() - 1);
                    if (Character.isDigit(lastUserNameChar)) {
                        user.setUsername(userDTO.getFirstName().toLowerCase()
                                + "." + userDTO.getLastName().toLowerCase()
                                + (Character.getNumericValue(lastUserNameChar) + 1));
                    } else {
                        user.setUsername(userDTO.getFirstName().toLowerCase()
                                + "." + userDTO.getLastName().toLowerCase() + 1);
                    }
                }, () -> user.setUsername(userDTO.getFirstName().toLowerCase() + "."
                        + userDTO.getLastName().toLowerCase()));

        var employeeAuthority = authorityRepository.findById(AuthoritiesConstants.EMPLOYEE.getRole())
                .orElseThrow(() -> new AuthorityNotExistsException("User cannot be created because data integrity has been compromised." +
                        " Check authority data in the database."));
        user.getAuthorities().add(employeeAuthority);

        log.debug(user.getUsername() + " account activation key: " + activationKey);
        userRepository.save(user);
        // TODO send mail with activation key
        return userMapper.mapToDTO(user);
    }

    @Override
    public UserDTO activateUser(String username, String password, String activationKey) {

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found", "User " + username + " not exists"));

        if (user.getActivated()) {
            throw new InvalidDataException("Activation error", "The account has already been activated");
        }

        if (passwordEncoder.matches(activationKey, user.getActivationKey())) {
            user.setActivationKey(null);
            user.setPassword(passwordEncoder.encode(password));
            user.setActivated(true);
            userRepository.save(user);
            return userMapper.mapToDTO(user);
        } else {
            throw new InvalidDataException("Incorrect activation key", "Your activation key is incorrect");
        }
    }
}
