package pl.kubaretip.cpo.api.service.impl;

import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Service;
import pl.kubaretip.cpo.api.domain.User;
import pl.kubaretip.cpo.api.service.MailService;
import pl.kubaretip.cpo.api.service.UserActivationReportService;
import pl.kubaretip.cpo.api.service.UserActivationService;

import java.io.FileNotFoundException;

@Service
public class UserActivationServiceImpl implements UserActivationService {

    private final UserActivationReportService userActivationReportService;
    private final MailService mailService;

    public UserActivationServiceImpl(UserActivationReportService userActivationReportService,
                                     MailService mailService) {
        this.userActivationReportService = userActivationReportService;
        this.mailService = mailService;
    }

    @Override
    public void generateUserActivationReport(User user, String activationKey) {
        try {
            userActivationReportService.createActivationUserReport(user, activationKey);
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendUserActivationMail(User user, String activationKey, User accountCreatedBy) {
        mailService.sendUserActivationEmail(user, activationKey, accountCreatedBy);
    }
}
