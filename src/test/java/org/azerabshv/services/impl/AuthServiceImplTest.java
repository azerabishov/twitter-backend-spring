package org.azerabshv.services.impl;


import org.azerabshv.dto.request.SignupRequest;
import org.azerabshv.models.User;
import org.azerabshv.repository.user.UserRepository;
import org.azerabshv.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
//
//    @Captor
//    private ArgumentCaptor<User> userArgumentCaptor;
//
//    @Mock
//    private  EmailServiceImpl emailService;
//    @Mock
//    private  UserRepository userRepository;
//    @Mock
//    private  JwtUtils jwtUtils;
//
////    private  PasswordEncoder encoder;
//
////    private  AuthenticationManager authenticationManager;
//
//    private AuthServiceImpl authService;
//
//    @BeforeEach
//    void setup() {
//        this.authService = new AuthServiceImpl(emailService, userRepository, null, jwtUtils, null);
//    }


    @Test
    void register() {
//        User user = new User("testuser",
//                "Test user",
//                "test@gmail.com",
//                "+9944444444",
//                new Date(),
//                "test1234",
//                new Date());
//
//        SignupRequest signupRequest = new SignupRequest("test@gmail.com",
//                "testuser",
//                "test1234",
//                "+9944444444",
//                "Test user",
//                new Date());
//
//
//        when(userRepository.findByUsername(signupRequest.getUsername())).thenReturn(Optional.of(user));
//        when(userRepository.findByEmail(signupRequest.getEmail())).thenReturn(Optional.of(user));
//        authService.register(signupRequest);
//        verify(userRepository, Mockito.times(1)).save(userArgumentCaptor.capture());
//        assertThat(userArgumentCaptor.getValue().getEmail()).isEqualTo("test@gmail.com");
//        assertThat(userArgumentCaptor.getValue().getUsername()).isEqualTo("testuser");
    }

    @Test
    void login() {
    }

    @Test
    void sendForgotPasswordEmail() {
    }

    @Test
    void resetPassword() {
    }

    @Test
    void createUser() {
    }

    @Test
    void updateUserPassword() {
    }

    @Test
    void updateUserForgotPasswordToken() {
    }
}