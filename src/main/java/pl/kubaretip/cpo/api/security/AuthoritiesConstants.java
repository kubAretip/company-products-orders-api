package pl.kubaretip.cpo.api.security;

public enum AuthoritiesConstants {
    SUPERVISOR("ROLE_SUPERVISOR"),
    EMPLOYEE("ROLE_EMPLOYEE"),
    STOREKEEPER("ROLE_STOREKEEPER"),
    EXECUTOR("ROLE_EXECUTOR");

    private final String role;

    AuthoritiesConstants(String role) {
        this.role = role;
    }

    public String role() {
        return this.role;
    }

}
