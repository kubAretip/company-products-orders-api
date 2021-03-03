package pl.kubaretip.cpo.api.constants;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AuthoritiesConstants implements GrantedAuthority {
    ROLE_USER(Code.USER),
    ROLE_SUPERVISOR(Code.SUPERVISOR),
    ROLE_MARKETER(Code.MARKETER),
    ROLE_STOREKEEPER(Code.STOREKEEPER),
    ROLE_EXECUTOR(Code.EXECUTOR),
    ROLE_MODERATOR(Code.MODERATOR);

    private final String authority;


    @Override
    public String getAuthority() {
        return authority;
    }

    public static class Code {
        public static final String USER = "ROLE_USER";
        public static final String SUPERVISOR = "ROLE_SUPERVISOR";
        public static final String MARKETER = "ROLE_MARKETER";
        public static final String STOREKEEPER = "ROLE_STOREKEEPER";
        public static final String EXECUTOR = "ROLE_EXECUTOR";
        public static final String MODERATOR = "ROLE_MODERATOR";

    }

}
