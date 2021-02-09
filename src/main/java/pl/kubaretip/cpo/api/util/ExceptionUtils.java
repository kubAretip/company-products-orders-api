package pl.kubaretip.cpo.api.util;

import org.springframework.stereotype.Component;
import pl.kubaretip.cpo.api.exception.InvalidDataException;
import pl.kubaretip.cpo.api.exception.NotFoundException;

@Component
public class ExceptionUtils {

    private final Translator translator;

    public ExceptionUtils(Translator translator) {
        this.translator = translator;
    }

    public NotFoundException userNotFound(String username) {
        return new NotFoundException(translator.translate("user.notFound.title"),
                translator.translate("user.notFound.username.message", new Object[]{username}));
    }

    public NotFoundException userNotFound(long userId) {
        return new NotFoundException(translator.translate("user.notFound.title"),
                translator.translate("user.notFound.id.message", new Object[]{userId}));
    }

    public InvalidDataException pathIdNotEqualsBodyId() {
        return new InvalidDataException(translator.translate("common.badRequest.title"),
                translator.translate("common.pathIdNotEqualsBodyId.message"));
    }


}
