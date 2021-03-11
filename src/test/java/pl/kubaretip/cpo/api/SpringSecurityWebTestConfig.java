package pl.kubaretip.cpo.api;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.kubaretip.cpo.api.constants.AuthoritiesConstants;
import pl.kubaretip.cpo.api.domain.Authority;
import pl.kubaretip.cpo.api.domain.User;
import pl.kubaretip.cpo.api.repository.UserRepository;
import pl.kubaretip.cpo.api.security.UserDetailsServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@TestConfiguration
public class SpringSecurityWebTestConfig {

    public static final String MODERATOR_USER = "marketer";
    public static final String MARKETER_USER = "moderator";

    @Bean
    @Primary
    public UserDetailsService testUserDetailsService() {

        var userRepository = mock(UserRepository.class);

        var marketer = baseUser("marketer", 1);
        setAuthority(marketer, AuthoritiesConstants.ROLE_MARKETER);

        var moderator = baseUser("moderator", 2);
        setAuthority(moderator, AuthoritiesConstants.ROLE_MODERATOR);

        var user = baseUser("user", 3);

        given(userRepository.findByUsernameIgnoreCase("marketer")).willReturn(Optional.of(marketer));
        given(userRepository.findByUsernameIgnoreCase("moderator")).willReturn(Optional.of(moderator));
        given(userRepository.findByUsernameIgnoreCase("user")).willReturn(Optional.of(user));

        return new UserDetailsServiceImpl(userRepository);
    }

    private void setAuthority(User user, AuthoritiesConstants authoritiesConstants) {
        var authority = new Authority();
        authority.setName(authoritiesConstants.name());
        user.getAuthorities().add(authority);
    }

    private User baseUser(String username, long id) {
        var user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName("userFirstName");
        user.setLastName("userLastName");
        user.setActivated(true);
        user.setPassword("password");
        var userAuthority = new Authority();
        userAuthority.setName(AuthoritiesConstants.ROLE_USER.name());
        user.setAuthorities(new HashSet<>(List.of(userAuthority)));
        return user;
    }


}
