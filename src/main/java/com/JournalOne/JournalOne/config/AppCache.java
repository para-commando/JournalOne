package com.JournalOne.JournalOne.config;

import com.JournalOne.JournalOne.entity.Configs;
import com.JournalOne.JournalOne.repository.ConfigsRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keyNames{
        KEY_NAME_ONE
    }

    public Map<String, String> APP_CACHE;

    @Autowired
    private ConfigsRepo configsRepo;

  //  when @Component creates a bean of this class when java application is loaded, right after that the below method is called and here we are using it to import some of the configs necessary to run the application and use it as an in memory cache
    @PostConstruct
    public void init(){

        //
        APP_CACHE= new HashMap<>();
        // this gets all the configs stored in db and stores in the variable declared above and it acts like a json object containing key value pairs
        List<Configs> allConfigs = configsRepo.findAll();
        for(Configs configs: allConfigs){
            APP_CACHE.put(configs.getKey(),configs.getValue());
        }

    }

}
