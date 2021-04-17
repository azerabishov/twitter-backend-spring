package org.azerabshv.controllers;

import org.azerabshv.dto.request.ForgotPasswordRequest;
import org.azerabshv.dto.request.LoginRequest;
import org.azerabshv.dto.request.ResetPasswordRequest;
import org.azerabshv.dto.request.SignupRequest;
import org.azerabshv.dto.response.LoginResponse;
import org.azerabshv.dto.response.MessageResponse;
import org.azerabshv.models.User;
import org.azerabshv.repository.user.UserRepository;
import org.azerabshv.security.UserDetailsImpl;
import org.azerabshv.utils.JwtUtils;
import org.azerabshv.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.Optional;


@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder encoder;


    @Autowired
    AuthService authService;

    @PostMapping("signin")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        String token = jwtUtils.generateToken(userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(userPrincipal.getId(), userPrincipal.getEmail(), userPrincipal.getUsername(), token));
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (signupRequest.getEmail() == null && signupRequest.getPhone_number() == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Please choose one of the Sign up option (email or phone number)!"));
        }

        if(signupRequest.getEmail() != null && userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already exists!"));
        }

        if(signupRequest.getPhone_number() != null && userRepository.existsByPhoneNumber(signupRequest.getPhone_number())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Phone number is already exists!"));
        }

        if(userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }
        User user = new User(signupRequest.getUsername(),
                             signupRequest.getScreen_name(),
                             signupRequest.getEmail(),
                             signupRequest.getPhone_number(),
                             signupRequest.getBirthday(),
                             encoder.encode(signupRequest.getPassword()),
                             new Date());

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("User registered successfully!"));
    }


    @PostMapping("/forgot/password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) throws MessagingException {
        if(!userRepository.existsByEmail(forgotPasswordRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Email not found"));
        }

        authService.sendForgotPasswordEmail(forgotPasswordRequest.getEmail());

        return ResponseEntity.status(HttpStatus.OK).body("Please check your email");
    }

    @PostMapping("/reset/password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) throws MessagingException {
        Optional<User> userData = userRepository.findByForgotPasswordToken(resetPasswordRequest.getToken());

        if(userData.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Token not exist!"));
        }

        User user = userData.get();

        if(user.getForgotPasswordTokenCreatedAt().before(new Date())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Token is expire!"));
        }

        authService.resetPassword(user, encoder.encode(resetPasswordRequest.getPassword()));



        return ResponseEntity.status(HttpStatus.OK).body("Your password has been reset");
    }

}
