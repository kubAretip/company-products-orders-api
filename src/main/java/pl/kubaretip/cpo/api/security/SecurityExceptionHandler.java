package pl.kubaretip.cpo.api.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import pl.kubaretip.cpo.api.exception.model.Error;
import pl.kubaretip.cpo.api.security.exception.InvalidLoginRequestException;
import pl.kubaretip.cpo.api.util.Translator;

@RestControllerAdvice
public class SecurityExceptionHandler {

    private final Translator translator;

    public SecurityExceptionHandler(Translator translator) {
        this.translator = translator;
    }

    @ExceptionHandler(InvalidLoginRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleInvalidLoginRequest(InvalidLoginRequestException ex, WebRequest webRequest) {
        return Error.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(translator.translate("common.invalidLoginRequest.message"))
                .title(translator.translate("common.invalidLoginRequest.title"))
                .build();
    }

}
