package pl.kubaretip.cpo.api.exception;

public class OrderStatusException extends BusinessLogicException {
    public OrderStatusException(String title, String message) {
        super(title, message);
    }
}
