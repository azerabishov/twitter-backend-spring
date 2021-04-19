package org.azerabshv.controllers;

import org.azerabshv.dto.request.ForgotPasswordRequest;
import org.azerabshv.dto.request.LoginRequest;
import org.azerabshv.dto.request.ResetPasswordRequest;
import org.azerabshv.dto.request.SignupRequest;
import org.azerabshv.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;


@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("signin")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("signup")
    public void registerUser(@RequestBody SignupRequest signupRequest) {
        authService.register(signupRequest);
    }


    @PostMapping("/forgot/password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) throws MessagingException {
        authService.sendForgotPasswordEmail(forgotPasswordRequest.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body("Please check your email");
    }

    @PostMapping("/reset/password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) throws MessagingException {
        return authService.resetPassword(resetPasswordRequest);
    }
}
