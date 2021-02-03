package pl.kubaretip.cpo.api.exception;

public class AlreadyExistsException extends BusinessLogicException {

    public AlreadyExistsException(String title, String message) {
        super(title, message);
    }
}
