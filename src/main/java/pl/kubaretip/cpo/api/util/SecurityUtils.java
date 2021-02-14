package pl.kubaretip.cpo.api.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.kubaretip.cpo.api.security.SecurityUser;

import java.util.Optional;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static Optional<String> getCurrentUserLogin() {
        var securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(parseAuthentication(securityContext.getAuthentication()));
    }

    private static String parseAuthentication(Authentication authentication) {
        if (authentication == null) {
            return null;
        }

        if (authentication.getPrincipal() instanceof SecurityUser) {
            var securityUser = (SecurityUser) authentication.getPrincipal();
            return securityUser.getUsername();
        }

        if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }

        return null;
    }


}
