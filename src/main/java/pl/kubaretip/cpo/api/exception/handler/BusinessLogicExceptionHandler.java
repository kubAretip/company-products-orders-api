package pl.kubaretip.cpo.api.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import pl.kubaretip.cpo.api.exception.AlreadyExistsException;
import pl.kubaretip.cpo.api.exception.AuthorityNotExistsException;
import pl.kubaretip.cpo.api.exception.InvalidDataException;
import pl.kubaretip.cpo.api.exception.NotFoundException;
import pl.kubaretip.cpo.api.exception.model.Error;

@RestControllerAdvice
public class BusinessLogicExceptionHandler {

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Error handleAlreadyExists(AlreadyExistsException ex, WebRequest request) {
        return Error.builder()
                .title(ex.getTitle())
                .status(HttpStatus.CONFLICT.value())
                .detail(ex.getMessage())
                .build();
    }

    @ExceptionHandler(AuthorityNotExistsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleAuthorityNotExists(AuthorityNotExistsException ex, WebRequest request) {
        return Error.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(ex.getMessage())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleNotFound(NotFoundException ex, WebRequest request) {
        return Error.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .title(ex.getTitle())
                .detail(ex.getMessage())
                .build();
    }

    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleInvalidData(InvalidDataException ex, WebRequest request) {
        return Error.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .title(ex.getTitle())
                .detail(ex.getMessage())
                .build();
    }


}
