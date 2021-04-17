package org.azerabshv.services.impl;

import org.azerabshv.models.Mail;
import org.azerabshv.models.User;
import org.azerabshv.repository.user.UserRepository;
import org.azerabshv.services.AuthService;
import org.azerabshv.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    @Autowired
    EmailServiceImpl emailService;

    @Autowired
    UserRepository userRepository;

    @Override
    public void sendForgotPasswordEmail(String email) throws MessagingException {
        String token = StringUtil.generateToken();
        Map map = new HashMap();
        map.put("token",token);
        Mail mail = new Mail();
        mail.setMailTo(email);
        mail.setFrom("abishov@gmail.com");
        mail.setSubject("Reset password");
        mail.setProps(map);
        updateUserForgotPasswordToken(email, token);
        emailService.sendMail(mail);
    }

    @Override
    public void updateUserForgotPasswordToken(String email, String token) {
        Optional<User> userData= userRepository.findByEmail(email);
//        if(userData.isPresent()) {
            User user = userData.get();
            user.setForgotPasswordToken(token);
            user.setForgotPasswordTokenCreatedAt(new Date(System.currentTimeMillis() + 1000*60*60*10));
            userRepository.save(user);
//        } else {
//
//        }
    }

    @Override
    public void resetPassword(User user, String password) {
        user.setPassword(password);
        userRepository.save(user);
    }


}
