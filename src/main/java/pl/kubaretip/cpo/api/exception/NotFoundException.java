package pl.kubaretip.cpo.api.exception;

public class NotFoundException extends BusinessLogicException{
    public NotFoundException(String title, String message) {
        super(title, message);
    }
}
