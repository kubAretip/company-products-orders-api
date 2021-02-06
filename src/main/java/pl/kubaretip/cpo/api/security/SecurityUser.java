package pl.kubaretip.cpo.api.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.kubaretip.cpo.api.domain.User;

import java.util.Collection;
import java.util.stream.Collectors;

public class SecurityUser implements UserDetails {

    private final String username;
    private final String password;
    @Getter
    private final Long id;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;

    public SecurityUser(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.authorities = authorities;
        this.enabled = true;
    }

    public SecurityUser(User user) {
        this.password = user.getPassword();
        this.id = user.getId();
        this.username = user.getUsername();
        this.authorities = user.getAuthorities()
                .stream().map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
        this.enabled = user.getActivated();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
