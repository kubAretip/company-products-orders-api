package pl.kubaretip.cpo.api.config;

public final class AppConstants {

    // only 4 chars length in regexp because information about length coming from other validation rules (@Size)
    // when you use this regexp not using JSR 303 with @Size annotation make sure you validate password length
    public final static String PASSWORD_REGEXP = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{4,}$";
    public final static int PASSWORD_MIN_LENGTH = 8;
    public final static int PASSWORD_MAX_LENGTH = 20;
    public final static String JWT_HEADER = "Authorization";
    public final static String JWT_PREFIX = "Bearer ";
    public final static String AUTHENTICATE_ENDPOINT = "/authenticate";
    public final static String TIME_ZONE_UTC = "UTC";

    private AppConstants() {
    }

}
