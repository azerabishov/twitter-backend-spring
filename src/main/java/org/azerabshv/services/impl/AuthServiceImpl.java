package org.azerabshv.services.impl;

import lombok.RequiredArgsConstructor;
import org.azerabshv.dto.request.LoginRequest;
import org.azerabshv.dto.request.ResetPasswordRequest;
import org.azerabshv.dto.request.SignupRequest;
import org.azerabshv.dto.response.LoginResponse;
import org.azerabshv.exception.*;
import org.azerabshv.models.User;
import org.azerabshv.repository.user.UserRepository;
import org.azerabshv.security.UserDetailsImpl;
import org.azerabshv.services.AuthService;
import org.azerabshv.services.EmailService;
import org.azerabshv.utils.JwtUtils;
import org.azerabshv.utils.StringUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final EmailService emailService;

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    @Override
    public void register(SignupRequest signupRequest) throws MessagingException {
        userRepository.findByUsername(signupRequest.getUsername())
                .ifPresent(user -> { throw new UsernameAlreadyExistsException(); });
        userRepository.findByEmail(signupRequest.getEmail())
                .ifPresent(user -> { throw new EmailAlreadyExistsException(); });
        createUser(signupRequest);
        sendEmailVerificationCode(signupRequest.getEmail());
    }



    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
//        bunu yoxla
//        authentication.isAuthenticated()
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        if(!userPrincipal.getIsEmailVerified()) {
            throw new EmailNotVerifiedException();
        }

        String token = jwtUtils.generateToken(userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(userPrincipal.getId(), userPrincipal.getEmail(), userPrincipal.getUsername(), token));

    }



    @Override
    public void sendForgotPasswordEmail(String email) throws MessagingException {
        String token = StringUtil.generateToken();

        userRepository.findByEmail(email)
                .map(user -> updateUserForgotPasswordToken(user, token))
                .orElseThrow(UserNotFoundException::new);

        emailService.sendForgotPasswordMail(token, email);
    }



    @Override
    public ResponseEntity<?> verifyEmail(Integer verificationCode) {
        userRepository.findByEmailVerificationCode(verificationCode)
                .map(this::verifyUserEmail)
                .orElseThrow(InvalidTokenException::new);

        return ResponseEntity.status(HttpStatus.OK).body("Your password has been reset");
    }


    @Override
    public ResponseEntity<?> resetPassword(ResetPasswordRequest resetPasswordRequest) {
        userRepository.findByForgotPasswordToken(resetPasswordRequest.getToken())
                .map(user -> updateUserPassword(user, resetPasswordRequest.getPassword()))
                .orElseThrow(InvalidTokenException::new);

        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();
        return userPrincipal.getId();
    }

    public void createUser(SignupRequest signupRequest) {
        User user = new User(signupRequest.getUsername(),
                signupRequest.getScreen_name(),
                signupRequest.getEmail(),
                signupRequest.getPhone_number(),
                signupRequest.getBirthday(),
                encoder.encode(signupRequest.getPassword()),
                new Date());

        userRepository.save(user);
    }

    public User updateUserPassword(User user, String password) {
        if(user.getForgotPasswordTokenCreatedAt().before(new Date())){
            throw new InvalidTokenException();
        }

        user.setPassword(encoder.encode(password));
        return userRepository.save(user);
    }

    public User verifyUserEmail(User user) {
        if(user.getEmailVerificationCodeExpireIn().before(new Date())){
            throw new InvalidTokenException();
        }

        user.setEmailVerified(true);
        return userRepository.save(user);
    }

    public void sendEmailVerificationCode(String email) throws MessagingException {
        int verificationCode = (int) (Math.random()*(999999-100000)) - +100000;
        userRepository.findByEmail(email)
                .map(user -> updateEmailVerificationCode(user, verificationCode))
                .orElseThrow(UserNotFoundException::new);

        emailService.sendEmailVerificationMail(verificationCode, email);
    }




    public User updateUserForgotPasswordToken(User user, String token) {
        user.setForgotPasswordToken(token);
        user.setForgotPasswordTokenCreatedAt(new Date(System.currentTimeMillis() + 1000*60*60*10));
        return userRepository.save(user);
    }

    public User updateEmailVerificationCode(User user, Integer verificationCode) {
        user.setEmailVerificationCode(verificationCode);
        user.setEmailVerificationCodeExpireIn(new Date(System.currentTimeMillis() + 1000*60*60*10));
        return userRepository.save(user);
    }

}
