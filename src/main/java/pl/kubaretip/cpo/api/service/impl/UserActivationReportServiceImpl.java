package pl.kubaretip.cpo.api.service.impl;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import pl.kubaretip.cpo.api.domain.User;
import pl.kubaretip.cpo.api.domain.UserActivationReport;
import pl.kubaretip.cpo.api.exception.NotFoundException;
import pl.kubaretip.cpo.api.repository.UserActivationReportRepository;
import pl.kubaretip.cpo.api.service.UserActivationReportService;
import pl.kubaretip.cpo.api.util.Translator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class UserActivationReportServiceImpl implements UserActivationReportService {

    private final UserActivationReportRepository userActivationReportRepository;
    private final Translator translator;

    public UserActivationReportServiceImpl(UserActivationReportRepository userActivationReportRepository,
                                           Translator translator) {
        this.userActivationReportRepository = userActivationReportRepository;
        this.translator = translator;
    }

    @Override
    public void createActivationUserReport(User user, String activationKey) throws DocumentException {
        var userActivationReport = new UserActivationReport();
        byte[] data = generateUserActivationReport(user, activationKey);
        userActivationReport.setUser(user);
        userActivationReport.setData(data);
        userActivationReport.setType(MediaType.APPLICATION_PDF_VALUE);
        userActivationReport.setName(user.getUsername() + ".pdf");
        userActivationReportRepository.save(userActivationReport);
    }

    @Override
    public UserActivationReport getUserActivationReport(long userId) {
        return userActivationReportRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(translator.translate("common.notFound.title"),
                        translator.translate("userActivationReport.notFound", new Object[]{userId})));
    }

    public byte[] generateUserActivationReport(User user, String activationKey) throws DocumentException {
        var document = new Document();
        var byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();
        addUserInformationToReport(document, user, activationKey);
        document.close();
        var byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return byteArrayInputStream.readAllBytes();
    }

    public void addUserInformationToReport(Document document, User user, String activationKey) throws DocumentException {
        var paragraph = new Paragraph();
        var normalFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, BaseColor.BLACK);
        var titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(new Paragraph(translator.translate("pdf.userActivation.title"), titleFont));
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(new Paragraph(new Chunk(translator.translate("pdf.userActivation.firstAndLastName")
                + " " + user.getFirstName() + " " + user.getLastName(), normalFont)));
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(new Paragraph(new Chunk(translator.translate("pdf.userActivation.username")
                + " " + user.getUsername(), normalFont)));
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(new Paragraph(new Chunk(translator.translate("pdf.userActivation.email")
                + " " + user.getEmail(), normalFont)));
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(new Paragraph(new Chunk(translator.translate("pdf.userActivation.activationKey")
                + " " + activationKey, normalFont)));
        document.add(paragraph);
    }


}
