package pl.kubaretip.cpo.api.exception;

public class InvalidDataException extends BusinessLogicException {

    public InvalidDataException(String message, Object[] args) {
        super(message, args);
    }

    public InvalidDataException(String message) {
        super(message);
    }
}
