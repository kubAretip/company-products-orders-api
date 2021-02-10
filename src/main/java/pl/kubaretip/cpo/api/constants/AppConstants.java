package pl.kubaretip.cpo.api.constants;

public final class AppConstants {

    public final static int PASSWORD_MIN_LENGTH = 8;
    public final static int PASSWORD_MAX_LENGTH = 20;
    public final static String JWT_HEADER = "Authorization";
    public final static String JWT_PREFIX = "Bearer ";
    public final static String AUTHENTICATE_ENDPOINT = "/authenticate";
    public final static String TIME_ZONE_UTC = "UTC";
    public final static int USER_ACTIVATION_KEY_LENGTH = 9;

    private AppConstants() {
    }

}
