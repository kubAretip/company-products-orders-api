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
        return new NotFoundException(translator.translate("exception.user.notFound.title"),
                translator.translate("exception.user.notFound.message", new Object[]{username}));
    }

    public NotFoundException userNotFound(long userId) {
        return new NotFoundException(translator.translate("exception.user.notFound.title"),
                translator.translate("exception.user.notFound.message2", new Object[]{userId}));
    }

    public InvalidDataException pathIdNotEqualsBodyId() {
        return new InvalidDataException(translator.translate("exception.common.badRequest.title"),
                translator.translate("exception.common.pathIdNotEqualsBodyId.message"));
    }


}
