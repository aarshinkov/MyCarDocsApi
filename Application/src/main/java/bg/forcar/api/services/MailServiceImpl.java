package bg.forcar.api.services;

import bg.forcar.api.entities.MailEntity;
import bg.forcar.api.beans.ConfigurationBean;
import bg.forcar.api.beans.RandomBuilder;
import bg.forcar.api.repositories.MailsRepository;
import bg.forcar.api.tasks.MailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Service
public class MailServiceImpl implements MailService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MailsRepository mailsRepository;

    @Autowired
    private ConfigurationBean configBean;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private RandomBuilder randomBuilder;

//    @Autowired
//    private TemplateEngine templateEngine;
    @Autowired
    private MessageSource messageSource;

    @Override
    public MailEntity createMail(String sender, String subject, String content, String... recipients) {

        StringBuilder to = new StringBuilder();

        for (String recipient : recipients) {
            to.append(recipient);
            to.append(";");
        }

        String recips = to.substring(0, to.length() - 1);

        MailEntity mail = new MailEntity();
        mail.setSender(sender);
        mail.setReceivers(recips);
        mail.setSubject(subject);
        mail.setContent(content);

        return mailsRepository.save(mail);
    }

    @Override
    public void sendResetPasswordMail(String email, String code) {
        StringBuilder builder = new StringBuilder();
        builder.append("Hello, user<br>");
        builder.append("You have requested to reset your password.<br>");

        final String url = "http://mycardocs.com/api/password?c=" + code;
        builder.append("<a href=\"").append(url).append("\">").append(url).append("</a>");
        String htmlContent = String.valueOf(builder);

//      String htmlContent = templateEngine.process("warningStoryMail.html", ctx);
        MailEntity mail = createMail(configBean.getEmailSettings().getSender(), "Password reset", htmlContent, email);

        mailSender.sendMail(mail, email);
    }
}
