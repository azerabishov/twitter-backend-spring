package org.azerabshv.services;

import org.azerabshv.dto.request.LoginRequest;
import org.azerabshv.dto.request.ResetPasswordRequest;
import org.azerabshv.dto.request.SignupRequest;
import org.azerabshv.dto.response.LoginResponse;
import org.azerabshv.models.User;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;

public interface AuthService {
    User getAuthenticatedUser();
    Long getAuthenticatedUserId();
    void register(SignupRequest signupRequest) throws MessagingException;
    ResponseEntity<LoginResponse> login(LoginRequest loginRequest);
    void sendForgotPasswordEmail(String email) throws MessagingException;
    ResponseEntity<?> resetPassword(ResetPasswordRequest resetPasswordRequest);
    ResponseEntity<?> verifyEmail(Integer verificationCode);

}
