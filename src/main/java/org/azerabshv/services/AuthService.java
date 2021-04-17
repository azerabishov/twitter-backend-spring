package org.azerabshv.services;

import org.azerabshv.models.User;

import javax.mail.MessagingException;

public interface AuthService {
    void sendForgotPasswordEmail(String email) throws MessagingException;
    void updateUserForgotPasswordToken(String email, String token);
    void resetPassword(User user, String password);
}
