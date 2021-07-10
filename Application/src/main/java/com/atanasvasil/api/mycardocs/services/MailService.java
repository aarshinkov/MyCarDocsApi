package com.atanasvasil.api.mycardocs.services;

import com.atanasvasil.api.mycardocs.entities.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public interface MailService {

    MailEntity createMail(String sender, String subject, String content, String... recipients);

    void sendResetPasswordMail(String email, String code);
}
