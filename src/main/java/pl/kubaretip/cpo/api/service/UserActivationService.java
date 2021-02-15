package pl.kubaretip.cpo.api.service;

import pl.kubaretip.cpo.api.domain.User;

public interface UserActivationService {

    void generateUserActivationReport(User user, String activationKey);

    void sendUserActivationMail(User user, String activationKey,User accountCreatedBy);
}
