package pl.kubaretip.cpo.api.exception;

public class NotFoundException extends BusinessLogicException {

    public NotFoundException(String message, Object[] args) {
        super(message, args);
    }

    public NotFoundException(String message) {
        super(message);
    }
}
