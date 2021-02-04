package pl.kubaretip.cpo.api.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Translator {

    private final MessageSource messageSource;

    public Translator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String translate(String messageCode) {
        return messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale());
    }

    public String translate(String messageCode, Object[] args) {
        return messageSource.getMessage(messageCode, args, LocaleContextHolder.getLocale());
    }

}
