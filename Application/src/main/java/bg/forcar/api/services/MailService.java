package bg.forcar.api.services;

import bg.forcar.api.entities.MailEntity;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public interface MailService {

    MailEntity createMail(String sender, String subject, String content, String... recipients);

    void sendResetPasswordMail(String email, String code);
}
