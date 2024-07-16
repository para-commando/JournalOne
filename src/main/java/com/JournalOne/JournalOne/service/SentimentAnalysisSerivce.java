package com.JournalOne.JournalOne.service;

import org.springframework.stereotype.Component;

@Component
public class SentimentAnalysisSerivce {
    public String getSentiment(String text){
        return "happy";
    }
}
