package pl.kubaretip.cpo.api.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ValidationError {

    private String field;
    private List<String> message;

}
