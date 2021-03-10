package pl.kubaretip.cpo.api.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.kubaretip.cpo.api.exception.*;
import pl.kubaretip.cpo.api.exception.model.Error;
import pl.kubaretip.cpo.api.util.Translator;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class BusinessLogicExceptionHandler {

    private Translator translator;

    public BusinessLogicExceptionHandler(Translator translator) {
        this.translator = translator;
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Error handleAlreadyExists(AlreadyExistsException ex, HttpServletRequest request) {
        return Error.builder()
                .title(translator.translate("error.title.conflict"))
                .status(HttpStatus.CONFLICT.value())
                .detail(translator.translate(ex.getMessage(), ex.getArgs()))
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler({AuthorityNotExistsException.class, UserResourceException.class, StatusResourceException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleAuthorityNotExists(BusinessLogicException ex, HttpServletRequest request) {
        return Error.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .title(translator.translate("error.title.internalServerError"))
                .detail(translator.translate(ex.getMessage(), ex.getArgs()))
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleNotFound(NotFoundException ex, HttpServletRequest request) {
        return Error.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .title(translator.translate("error.title.notFound"))
                .detail(translator.translate(ex.getMessage(), ex.getArgs()))
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler({InvalidDataException.class, OrderStatusException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleInvalidData(BusinessLogicException ex, HttpServletRequest request) {
        return Error.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .title(translator.translate("error.title.badRequest"))
                .detail(translator.translate(ex.getMessage(), ex.getArgs()))
                .path(request.getRequestURI())
                .build();
    }


}
