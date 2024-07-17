package com.JournalOne.JournalOne.service;

import com.JournalOne.JournalOne.enums.Sentiment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, Sentiment body)
    {
        try{
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body.toString());
            javaMailSender.send(mail);
        }catch (Exception e){
            log.error("Exception while sending mail");
        }
    }
}