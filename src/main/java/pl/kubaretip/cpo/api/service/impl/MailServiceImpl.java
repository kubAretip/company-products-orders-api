package pl.kubaretip.cpo.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.kubaretip.cpo.api.domain.User;
import pl.kubaretip.cpo.api.service.MailService;
import pl.kubaretip.cpo.api.util.Translator;

import javax.mail.MessagingException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final Translator translator;

    public MailServiceImpl(JavaMailSender mailSender,
                           TemplateEngine templateEngine,
                           Translator translator) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.translator = translator;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        var mimeMessage = this.mailSender.createMimeMessage();

        try {
            var mimeHelper = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            mimeHelper.setTo(to);
            mimeHelper.setSubject(subject);
            mimeHelper.setText(content, isHtml);

            mailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            log.warn("Emil wasn't sent to user {}", to, e);
        }
    }


    @Async
    @Override
    public void sendUserActivationEmail(User user, String activationKey, User accountCreatedBy) {
        var userEmail = user.getEmail();
        if (Strings.isNotEmpty(userEmail) && new EmailValidator().isValid(userEmail, null)) {
            log.debug("Sending email template lang = {} to {}", LocaleContextHolder.getLocale(), userEmail);
            var subject = translator.translate("mail.userActivation.subject");
            var context = new Context(LocaleContextHolder.getLocale());
            context.setVariable("user", user);
            context.setVariable("key", activationKey);
            context.setVariable("help", accountCreatedBy);
            var content = templateEngine.process("mail/userActivation", context);
            sendEmail(userEmail, subject, content, false, true);
        } else {
            throw new IllegalArgumentException("Invalid email");
        }
    }


}
