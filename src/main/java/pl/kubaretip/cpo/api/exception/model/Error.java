package pl.kubaretip.cpo.api.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class Error {

    private String title;
    private String detail;
    private int status;
    private Set<ValidationError> validationErrors;
}
