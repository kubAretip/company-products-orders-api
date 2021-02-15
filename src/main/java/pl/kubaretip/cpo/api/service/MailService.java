package pl.kubaretip.cpo.api.service;

import pl.kubaretip.cpo.api.domain.User;

public interface MailService {
    void sendUserActivationEmail(User user, String activationKey,User accountCreatedBy);
}
