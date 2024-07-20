package com.JournalOne.JournalOne.scheduler;

import com.JournalOne.JournalOne.entity.JournalOneEntries;
import com.JournalOne.JournalOne.entity.User;
import com.JournalOne.JournalOne.enums.Sentiment;
import com.JournalOne.JournalOne.repository.JournalEntryRepo;
import com.JournalOne.JournalOne.repository.criterias.UserRepoCritImpl;
import com.JournalOne.JournalOne.service.EmailService;
import com.JournalOne.JournalOne.service.SentimentAnalysisSerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
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
            List<Sentiment> sentiments = journalOneEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment,Integer> sentimentCounts = new HashMap<>();
            for(Sentiment sentiment: sentiments)
            {
                if(sentiment !=null){
//                    below logic counts the total number of occurances of each type of sentiment, lets say happy is seen only once then its value will be 1 and if sad is seen twice then  its value will be 1+ prev value(1) which is 2
                    sentimentCounts.put(sentiment,sentimentCounts.getOrDefault(sentiment,0)+1);
                }
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount =0;
            for(Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet())
            {
                if(entry.getValue() > maxCount)
                {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
           if(mostFrequentSentiment !=null) {
               emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", mostFrequentSentiment);
           }
           else {
               log.info("No data related to emotions were found...");
           }

        }
    }
}
