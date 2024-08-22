package com.JournalOne.JournalOne.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SentimentConsumerService {

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "weekly-sentiments", groupId = "console-consumer-89759")
    public void consume(String sentimentData){
        System.out.println(sentimentData);
       // whenever some message is sent to the above mentioned topic, the below code is executed
       sendEmail();
    }

    private void sendEmail()
    {
        emailService.sendEmail("paracommando.one@gmail.com", "Sentiment for last 7 days", "sentimentData");
    }


}
