package pl.kubaretip.cpo.api.exception;

public class InvalidDataException extends BusinessLogicException{
    public InvalidDataException(String title, String message) {
        super(title, message);
    }
}
