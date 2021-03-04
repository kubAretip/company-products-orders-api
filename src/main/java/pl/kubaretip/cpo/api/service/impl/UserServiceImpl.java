package pl.kubaretip.cpo.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kubaretip.cpo.api.constants.AuthoritiesConstants;
import pl.kubaretip.cpo.api.domain.User;
import pl.kubaretip.cpo.api.dto.UserDTO;
import pl.kubaretip.cpo.api.exception.*;
import pl.kubaretip.cpo.api.repository.UserRepository;
import pl.kubaretip.cpo.api.service.AuthorityService;
import pl.kubaretip.cpo.api.service.UserActivationService;
import pl.kubaretip.cpo.api.service.UserService;
import pl.kubaretip.cpo.api.util.SecurityUtils;
import pl.kubaretip.cpo.api.util.Translator;

import static pl.kubaretip.cpo.api.constants.AppConstants.USER_ACTIVATION_KEY_LENGTH;

@Slf4j
@Service
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Translator translator;
    private final AuthorityService authorityService;
    private final UserActivationService userActivationService;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           Translator translator,
                           AuthorityService authorityService,
                           UserActivationService userActivationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.translator = translator;
        this.authorityService = authorityService;
        this.userActivationService = userActivationService;
    }

    @Override
    public User createUser(UserDTO userDTO) {

        log.debug("Creating new user");
        throwExceptionWhenUserWithEmailAlreadyExists(userDTO.getEmail());

        var user = new User();

        // cancel creating user when not exists base role in database
        try {
            var employeeAuthority = authorityService.getAuthority(AuthoritiesConstants.ROLE_USER.name());
            user.getAuthorities().add(employeeAuthority);
        } catch (NotFoundException ex) {
            throw new AuthorityNotExistsException(translator.translate("authority.error.message"));
        }

        var activationKey = RandomStringUtils.randomNumeric(USER_ACTIVATION_KEY_LENGTH);
        user.setFirstName(StringUtils.capitalize(userDTO.getFirstName().toLowerCase()));
        user.setLastName(StringUtils.capitalize(userDTO.getLastName().toLowerCase()));
        user.setEmail(userDTO.getEmail().toLowerCase());
        user.setActivationKey(passwordEncoder.encode(activationKey));

        // generate username
        userRepository.findLastGeneratedUsernameForFirstAndLastName(user.getFirstName(), user.getLastName())
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

        log.debug(user.getUsername() + " account activation key: " + activationKey);
        userRepository.save(user);

        userActivationService.generateUserActivationReport(user, activationKey);

        var accountCreatedByModerator = SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findByUsernameIgnoreCase)
                .orElseThrow(() -> new UserResourceException(translator.translate("user.notFound.userResource")));
        userActivationService.sendUserActivationMail(user, activationKey, accountCreatedByModerator);
        return user;
    }

    @Override
    public User activateUser(String username, String password, String activationKey) {

        var user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> userNotFound(username));

        if (user.getActivated()) {
            throw new InvalidDataException(translator.translate("user.alreadyActivated.title"),
                    translator.translate("user.alreadyActivated.message"));
        }

        if (passwordEncoder.matches(activationKey, user.getActivationKey())) {
            user.setActivationKey(null);
            user.setPassword(passwordEncoder.encode(password));
            user.setActivated(true);
            userRepository.save(user);
            return user;
        } else {
            throw new InvalidDataException(translator.translate("user.alreadyActivated.title"),
                    translator.translate("user.incorrect.activationKey.message"));
        }
    }

    @Override
    public void assignUserToNewAuthority(Long userId, String role) {
        var user = getUserById(userId);
        user.getAuthorities().add(authorityService.getAuthority(role));
        userRepository.save(user);
    }

    @Override
    public void removeUserAuthority(Long userId, String role) {
        var user = getUserById(userId);
        var authority = authorityService.getAuthority(role);

        if (authority.getName().equals(AuthoritiesConstants.ROLE_USER.name()))
            throw new InvalidDataException(translator.translate("common.actionNotAllowed.title"),
                    translator.translate("user.notAllowed.removeBaseAuthority.message"));

        user.getAuthorities().remove(authority);
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> userNotFound(username));
    }


    @Override
    public User findUserByIdAndAuthority(long userId, AuthoritiesConstants authority) {
        return userRepository.findUserByUsernameAndAuthority(userId, authority.name())
                .orElseThrow(() -> new NotFoundException(translator.translate("user.notFound.title"),
                        translator.translate("user.notFound.id.authority",
                                new String[]{String.valueOf(userId), authority.name()})
                ));
    }

    private NotFoundException userNotFound(String username) {
        return new NotFoundException(translator.translate("user.notFound.title"),
                translator.translate("user.notFound.username.message", new Object[]{username}));
    }

    private NotFoundException userNotFound(long userId) {
        return new NotFoundException(translator.translate("user.notFound.title"),
                translator.translate("user.notFound.id.message", new Object[]{userId}));
    }

    void throwExceptionWhenUserWithEmailAlreadyExists(String email) {
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new AlreadyExistsException(translator.translate("user.alreadyInUse.email.title"),
                    translator.translate("user.alreadyInUse.email.message", new Object[]{email}));
        }
    }

    @Override
    public User getUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> userNotFound(userId));
    }

    @Override
    public User editUserById(UserDTO userDTO) {
        var user = getUserById(userDTO.getId());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UserDTO userDTO) {
        var user = getUserById(userDTO.getId());

        if (!user.getEmail().equalsIgnoreCase(userDTO.getEmail()))
            throwExceptionWhenUserWithEmailAlreadyExists(userDTO.getEmail());

        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        return userRepository.save(user);
    }


}
