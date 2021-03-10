package pl.kubaretip.cpo.api.exception;

public class AlreadyExistsException extends BusinessLogicException {

    public AlreadyExistsException(String message, Object[] args) {
        super(message, args);
    }
}
