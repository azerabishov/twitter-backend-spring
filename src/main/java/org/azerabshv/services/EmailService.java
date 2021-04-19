package org.azerabshv.services;

import org.azerabshv.models.Mail;

import javax.mail.MessagingException;

public interface EmailService {
    void sendMail(String token, String userEmal) throws MessagingException;
}
