package pl.kubaretip.cpo.api.service;

import com.itextpdf.text.DocumentException;
import pl.kubaretip.cpo.api.domain.User;
import pl.kubaretip.cpo.api.domain.UserActivationReport;

import java.io.FileNotFoundException;

public interface UserActivationReportService {
    void createActivationUserReport(User user, String activationKey) throws FileNotFoundException, DocumentException;

    UserActivationReport getUserActivationReport(long userId);
}
