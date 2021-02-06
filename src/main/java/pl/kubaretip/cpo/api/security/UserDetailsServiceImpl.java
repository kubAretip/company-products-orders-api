package pl.kubaretip.cpo.api.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.kubaretip.cpo.api.repository.UserRepository;

import javax.transaction.Transactional;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        var user = userRepository.findByUsernameIgnoreCase(login)
                .orElseThrow(() -> new UsernameNotFoundException("User " + login + " wasn't found in the database"));
        return new SecurityUser(user);
    }
}
