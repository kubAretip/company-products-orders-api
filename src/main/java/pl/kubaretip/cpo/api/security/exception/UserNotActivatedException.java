package pl.kubaretip.cpo.api.security.exception;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

public class UserNotActivatedException extends AuthenticationException {

    @Getter
    private String inactiveUser;

    public UserNotActivatedException(String msg, String inactiveUser) {
        super(msg);
        this.inactiveUser = inactiveUser;
    }
}
