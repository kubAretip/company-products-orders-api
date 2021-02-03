package pl.kubaretip.cpo.api.security;

import lombok.Getter;

public enum AuthoritiesConstants {
    SUPERVISOR("ROLE_SUPERVISOR"),
    EMPLOYEE("ROLE_EMPLOYEE"),
    STOREKEEPER("ROLE_STOREKEEPER"),
    EXECUTOR("ROLE_EXECUTOR");

    @Getter
    private final String role;

    AuthoritiesConstants(String role) {
        this.role = role;
    }
}
