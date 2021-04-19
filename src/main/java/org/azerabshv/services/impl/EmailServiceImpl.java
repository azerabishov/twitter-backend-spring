package org.azerabshv.services.impl;

import org.azerabshv.config.EmailConfig;
import org.azerabshv.models.Mail;
import org.azerabshv.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {


    private static final String EMAIL_TEMPLATE = "forgot-password-template";

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;



    private MimeMessage buildMimeMessage(Mail mail, String emailTemplate) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        helper.setTo(mail.getMailTo());
        helper.setText(this.getHtmlContent(mail, emailTemplate), true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());

        return message;
    }

    private String getHtmlContent(Mail mail, String emailTemplate) {
        Context context = new Context();
        context.setVariables(mail.getProps());
        String html = this.templateEngine.process(emailTemplate, context);

        return html;
    }

    @Override
    public void sendMail(String token, String userEmail) throws MessagingException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("token",token);
        Mail mail = new Mail();
        mail.setMailTo(userEmail);
        mail.setFrom("abishov@gmail.com");
        mail.setSubject("Reset password");
        mail.setProps(map);
        this.mailSender.send(this.buildMimeMessage(mail, EMAIL_TEMPLATE));
    }
}
