package pl.kubaretip.cpo.api.util;

public final class RegexpConstants {

    // only 4 chars length in regexp because information about length coming from other validation rules (@Size)
    // when you use this regexp not using JSR 303 with @Size annotation make sure you validate password length
    public final static String PASSWORD_REGEXP = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{4,}$";
    public final static String COUNTRY_CALLING_CODE_REGEXP = "^(?!-)[\\d-]+.*(?<!-)$";
    public final static String ONLY_LETTERS_REGEXP = "[a-zA-Z]+";
}
