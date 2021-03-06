package org.azerabshv.controllers;

import lombok.RequiredArgsConstructor;
import org.azerabshv.dto.request.LoginRequest;
import org.azerabshv.dto.request.ResetPasswordRequest;
import org.azerabshv.dto.request.SignupRequest;
import org.azerabshv.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;


@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("signin")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("signup")
    public void registerUser(@RequestBody SignupRequest signupRequest) throws MessagingException {
        authService.register(signupRequest);
    }


    @PostMapping("/forgot/password")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) throws MessagingException {
        authService.sendForgotPasswordEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body("Please check your email");
    }

    @PostMapping("/reset/password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) throws MessagingException {
        return authService.resetPassword(resetPasswordRequest);
    }


    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam Integer verificationCode)  {
        return authService.verifyEmail(verificationCode);
    }
}
