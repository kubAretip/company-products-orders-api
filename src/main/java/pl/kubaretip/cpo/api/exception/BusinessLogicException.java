package pl.kubaretip.cpo.api.exception;

import lombok.Getter;

@Getter
public abstract class BusinessLogicException extends RuntimeException {

    /**
     * Default title for business exception
     */
    private String title = "Application exception";

    public BusinessLogicException(String title, String message) {
        super(message);
        this.title = title;
    }

    public BusinessLogicException(String message) {
        super(message);
    }
}
