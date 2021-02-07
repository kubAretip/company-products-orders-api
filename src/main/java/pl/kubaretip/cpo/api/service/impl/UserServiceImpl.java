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
import pl.kubaretip.cpo.api.util.Translator;

@Slf4j
@Service
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthorityRepository authorityRepository;
    private final Translator translator;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper,
                           AuthorityRepository authorityRepository,
                           Translator translator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.authorityRepository = authorityRepository;
        this.translator = translator;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        log.debug("Creating new user");
        if (userRepository.existsByEmailIgnoreCase(userDTO.getEmail())) {
            throw new AlreadyExistsException(translator.translate("exception.email.alreadyInUse.title"),
                    translator.translate("exception.email.alreadyInUse.message", new Object[]{userDTO.getEmail()}));
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

        var employeeAuthority = authorityRepository.findById(AuthoritiesConstants.EMPLOYEE.role())
                .orElseThrow(() -> new AuthorityNotExistsException(translator.translate("exception.authority.notExists.message")));
        user.getAuthorities().add(employeeAuthority);

        log.debug(user.getUsername() + " account activation key: " + activationKey);
        userRepository.save(user);
        // TODO send mail with activation key
        return userMapper.mapToDTO(user);
    }

    @Override
    public UserDTO activateUser(String username, String password, String activationKey) {

        var user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new NotFoundException(translator.translate("exception.user.notFound.title"),
                        translator.translate("exception.user.notFound.message", new Object[]{username})));

        if (user.getActivated()) {
            throw new InvalidDataException(translator.translate("exception.user.activation.error.title"),
                    translator.translate("exception.user.activation.alreadyActivated.message"));
        }

        if (passwordEncoder.matches(activationKey, user.getActivationKey())) {
            user.setActivationKey(null);
            user.setPassword(passwordEncoder.encode(password));
            user.setActivated(true);
            userRepository.save(user);
            return userMapper.mapToDTO(user);
        } else {
            throw new InvalidDataException(translator.translate("exception.user.activation.error.title"),
                    translator.translate("exception.user.activation.incorrectKey.message"));
        }
    }
}
