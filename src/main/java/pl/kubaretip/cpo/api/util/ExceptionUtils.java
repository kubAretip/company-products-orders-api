package pl.kubaretip.cpo.api.util;

import pl.kubaretip.cpo.api.exception.InvalidDataException;

public final class ExceptionUtils {

    public static InvalidDataException invalidRequestId() {
        return new InvalidDataException("exception.invalid.id");
    }

}
