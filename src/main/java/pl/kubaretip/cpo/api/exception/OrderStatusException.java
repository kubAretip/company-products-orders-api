package pl.kubaretip.cpo.api.exception;

public class OrderStatusException extends BusinessLogicException {
    public OrderStatusException(String message) {
        super(message);
    }
}
