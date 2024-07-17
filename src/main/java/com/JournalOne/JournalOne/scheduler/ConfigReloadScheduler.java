package com.JournalOne.JournalOne.scheduler;

import com.JournalOne.JournalOne.config.AppCache;
import com.JournalOne.JournalOne.entity.JournalOneEntries;
import com.JournalOne.JournalOne.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConfigReloadScheduler {
    @Autowired
    AppCache appCache;
     @Scheduled(cron = "0 0/10 * * * ?")
    public void reloadConfigs() {
         try {
             appCache.init();
         } catch (Exception e)
         {
             System.out.println("Exception occurred while executing config reload cron...");
         }
    }
}
