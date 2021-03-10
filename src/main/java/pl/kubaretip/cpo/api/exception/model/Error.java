package pl.kubaretip.cpo.api.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class Error {

    @Builder.Default
    private final Date timestamp = new Date();
    private int status;
    private String path;
    private String title;
    private String detail;
    private Set<ValidationError> validationErrors;
}
