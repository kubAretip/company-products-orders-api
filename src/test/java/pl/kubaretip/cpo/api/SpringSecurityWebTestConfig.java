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

import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@TestConfiguration
public class SpringSecurityWebTestConfig {

    @Bean
    @Primary
    public UserDetailsService customUserDetailsService() {

        var userRepositoryMock = mock(UserRepository.class);

        var user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setFirstName("userFirstName");
        user.setLastName("userLastName");
        user.setActivated(true);
        user.setPassword("password");
        var userAuthority = new Authority();
        userAuthority.setName(AuthoritiesConstants.ROLE_USER.name());
        var marketerAuthority = new Authority();
        marketerAuthority.setName(AuthoritiesConstants.ROLE_MARKETER.name());
        user.setAuthorities(Set.of(userAuthority, marketerAuthority));

        given(userRepositoryMock.findByUsernameIgnoreCase(anyString())).willReturn(Optional.of(user));

        return new UserDetailsServiceImpl(userRepositoryMock);
    }

}
