package org.azerabshv.services.impl;

import org.azerabshv.dto.request.LoginRequest;
import org.azerabshv.dto.request.ResetPasswordRequest;
import org.azerabshv.dto.request.SignupRequest;
import org.azerabshv.dto.response.LoginResponse;
import org.azerabshv.exception.EmailAlreadyExistsException;
import org.azerabshv.exception.InvalidTokenException;
import org.azerabshv.exception.UserNotFoundException;
import org.azerabshv.exception.UsernameAlreadyExistsException;
import org.azerabshv.models.User;
import org.azerabshv.repository.user.UserRepository;
import org.azerabshv.security.UserDetailsImpl;
import org.azerabshv.services.AuthService;
import org.azerabshv.utils.JwtUtils;
import org.azerabshv.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AuthServiceImpl implements AuthService {
    @Autowired
    EmailServiceImpl emailService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public void register(SignupRequest signupRequest) {
        userRepository.findByUsername(signupRequest.getUsername())
                .ifPresent(user -> {
                    throw new UsernameAlreadyExistsException();
                });
        userRepository.findByEmail(signupRequest.getEmail())
                .ifPresent(user -> {
                    throw new EmailAlreadyExistsException();
                });

        createUser(signupRequest);
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
        String token = jwtUtils.generateToken(userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(userPrincipal.getId(), userPrincipal.getEmail(), userPrincipal.getUsername(), token));

    }



    @Override
    public void sendForgotPasswordEmail(String email) throws MessagingException {
        String token = StringUtil.generateToken();

        userRepository.findByEmail(email)
                .map(user -> updateUserForgotPasswordToken(user, token))
                .orElseThrow(UserNotFoundException::new);

        emailService.sendMail(token, email);
    }



    @Override
    public ResponseEntity<?> resetPassword(ResetPasswordRequest resetPasswordRequest) {
        userRepository.findByForgotPasswordToken(resetPasswordRequest.getToken())
                .map(user -> updateUserPassword(user, resetPasswordRequest.getPassword()))
                .orElseThrow(InvalidTokenException::new);

        return ResponseEntity.status(HttpStatus.OK).body("Your password has been reset");
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


    public User updateUserForgotPasswordToken(User user, String token) {
        user.setForgotPasswordToken(token);
        user.setForgotPasswordTokenCreatedAt(new Date(System.currentTimeMillis() + 1000*60*60*10));
        return userRepository.save(user);
    }

}
