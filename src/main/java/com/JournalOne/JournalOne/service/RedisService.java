package com.JournalOne.JournalOne.service;

import com.JournalOne.JournalOne.api.response.TasksApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    public <T>T get(String keyName, Class<T> taskApiResponseClass){

        try {
            Object o = redisTemplate.opsForValue().get(keyName);
            if(o!=null) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(o.toString(), taskApiResponseClass);
            }
            return null;

        } catch (Exception e) {
            log.error("Exception occurred while fetching data from redis...",e);
            throw new RuntimeException(e);
        }
    }

    public void set(String keyName,Object o, Long ttl){
        try {

            ObjectMapper mapper = new ObjectMapper();
            String jsonValue = mapper.writeValueAsString(o);
            // if ttl was set as -1 then it will have no expiry
             redisTemplate.opsForValue().set(keyName,jsonValue,ttl, TimeUnit.SECONDS);
            return;
        } catch (Exception e) {
            log.error("Exception occurred while fetching data from redis...",e);
            throw new RuntimeException(e);
        }
    }
}
