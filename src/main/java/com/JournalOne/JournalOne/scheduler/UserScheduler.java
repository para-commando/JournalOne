package com.JournalOne.JournalOne.scheduler;

import com.JournalOne.JournalOne.entity.JournalOneEntries;
import com.JournalOne.JournalOne.entity.User;
import com.JournalOne.JournalOne.repository.JournalEntryRepo;
import com.JournalOne.JournalOne.repository.criterias.UserRepoCritImpl;
import com.JournalOne.JournalOne.service.EmailService;
import com.JournalOne.JournalOne.service.SentimentAnalysisSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepoCritImpl userRepoCrit;

    @Autowired
    private SentimentAnalysisSerivce sentimentAnalysisSerivce;

    // used this https://www.cronmaker.com/ to generate the below exp
    //   @Scheduled(cron = "0 0 9 ? * SUN *")

    // runs every minutes every day,
  //  @Scheduled(cron = "0 0/1 * * * ?")
    public void fetchUserAndSendSentimentAnalysis() {
        List<User> users = userRepoCrit.getUserForSA();
        for (User user : users) {
            List<JournalOneEntries> journalOneEntries = user.getJournalOneEntriesList();
            List<String> journalOneEntriesFilteredList = journalOneEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getContent()).collect(Collectors.toList());

            String entry = String.join("", journalOneEntriesFilteredList);
            String sentiment = sentimentAnalysisSerivce.getSentiment(entry);
            emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", sentiment);

        }
    }
}
