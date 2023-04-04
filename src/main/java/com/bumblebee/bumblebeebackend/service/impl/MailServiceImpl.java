package com.bumblebee.bumblebeebackend.service.impl;

import com.bumblebee.bumblebeebackend.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/3/2023
 **/
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender mailSender;

    @Override
    public void mailSender (String subject, String content, String email) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(content,"text/html");
            mimeMessage.setFrom(new InternetAddress("bumblebestores@gmail.com"));
            mimeMessage.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(email));
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
