package pl.kubaretip.cpo.api.exception;

public class AuthorityNotExistsException extends BusinessLogicException{
    public AuthorityNotExistsException(String message) {
        super(message);
    }
}
