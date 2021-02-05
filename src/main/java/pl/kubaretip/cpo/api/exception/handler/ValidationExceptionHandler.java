package pl.kubaretip.cpo.api.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.kubaretip.cpo.api.exception.model.Error;
import pl.kubaretip.cpo.api.exception.model.ValidationError;
import pl.kubaretip.cpo.api.util.Translator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

    private final Translator translator;

    public ValidationExceptionHandler(Translator translator) {
        this.translator = translator;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        Set<ValidationError> validationErrors = new HashSet<>();
        ex.getFieldErrors()
                .forEach(fieldError -> validationErrors.stream()
                        .filter(validationError -> validationError.getField().equals(fieldError.getField()))
                        .findAny()
                        .ifPresentOrElse(validationError -> {
                                    validationError.getMessage().add(fieldError.getDefaultMessage());
                                },
                                () -> {
                                    validationErrors.add(new ValidationError(fieldError.getField(),
                                            new ArrayList<>(Collections.singletonList(fieldError.getDefaultMessage())))
                                    );
                                }
                        )
                );

        return ResponseEntity
                .badRequest()
                .body(Error.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title(translator.translate("common.validation.incorrect.title"))
                        .validationErrors(validationErrors)
                        .build()
                );
    }


}
