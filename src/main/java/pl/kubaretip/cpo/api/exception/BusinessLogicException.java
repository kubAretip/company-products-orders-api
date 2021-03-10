package pl.kubaretip.cpo.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class BusinessLogicException extends RuntimeException {

    private String message;
    private Object[] args;

    public BusinessLogicException(String message) {
        super(message);
        this.message = message;
    }
}
