package pl.kubaretip.cpo.api.util;

import org.springframework.stereotype.Component;
import pl.kubaretip.cpo.api.exception.InvalidDataException;

@Component
public class ExceptionUtils {

    private final Translator translator;

    public ExceptionUtils(Translator translator) {
        this.translator = translator;
    }

    public InvalidDataException pathIdNotEqualsBodyId() {
        return new InvalidDataException(translator.translate("common.badRequest.title"),
                translator.translate("common.pathIdNotEqualsBodyId.message"));
    }


}
