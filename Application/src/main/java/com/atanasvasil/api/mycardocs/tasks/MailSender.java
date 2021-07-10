package com.atanasvasil.api.mycardocs.tasks;

import com.atanasvasil.api.mycardocs.beans.ConfigurationBean;
import com.atanasvasil.api.mycardocs.beans.settings.*;
import com.atanasvasil.api.mycardocs.entities.*;
import java.util.*;
import javax.annotation.*;
import javax.mail.*;
import javax.mail.internet.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.*;
import org.springframework.jdbc.core.*;
import org.springframework.mail.*;
import org.springframework.mail.javamail.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Component
public class MailSender {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ConfigurationBean configBean;

    private JavaMailSender sender;

    @Async
    public void sendSimpleMail(SimpleMailMessage message) {
        try {
            sender.send(message);
        } catch (MailException e) {
            log.debug("Error sending mail!");
        }
    }

//    @Async
//    @Transactional(rollbackFor = Exception.class)
//    public void sendSignupMail(MailEntity mail, SignupMail signupMail) {
//        log.debug("Sending mail: " + mail.getMailId());
//
//        String sqlUpd = "UPDATE MAILBOX SET IS_SENT = ? WHERE MAIL_ID = ?";
//
//        try {
//            MimeMessage mimeMessage = sender.createMimeMessage();
//            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
//            message.setSubject(mail.getSubject());
//            message.setFrom(mail.getSender());
//            message.setTo(signupMail.getEmail());
//            message.setText(mail.getContent(), true); //true = isHtml
//
//            sender.send(mimeMessage);
//
//            jdbcTemplate.update(sqlUpd, true, mail.getMailId());
//            log.debug("Mail sent: " + mail.getMailId());
//        } catch (Exception e) {
//            log.error("Sending mail with id = " + mail.getMailId() + " failed!", e);
//        }
//    }
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void sendMail(MailEntity mail, String email) {
        log.debug("Sending mail: " + mail.getMailId());

        String sqlUpd = "UPDATE MAILBOX SET IS_SENT = ? WHERE MAIL_ID = ?";

        try {
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setSubject(mail.getSubject());
            message.setFrom(mail.getSender());
            message.setTo(email);
            message.setText(mail.getContent(), true); //true = isHtml

            sender.send(mimeMessage);

            jdbcTemplate.update(sqlUpd, true, mail.getMailId());
            log.debug("Mail sent: " + mail.getMailId());
        } catch (MessagingException | DataAccessException | MailException e) {
            log.error("Sending mail with id = " + mail.getMailId() + " failed!", e);
        }
    }

    @PostConstruct
    private void init() {
        JavaMailSenderImpl ms = new JavaMailSenderImpl();
        EmailSettings es = configBean.getEmailSettings();
        log.debug(es.toString());
        ms.setHost(es.getHost());
        ms.setPort(es.getPort());
        ms.setProtocol(es.getProtocol());
        ms.setUsername(es.getUsername());
        ms.setPassword(es.getPassword());

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.quitwait", "false");
        ms.setJavaMailProperties(properties);
        sender = ms;
    }
}
