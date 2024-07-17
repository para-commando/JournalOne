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

    public Object get(String keyName){

        try {
            Object o = redisTemplate.opsForValue().get(keyName);
//            ObjectMapper mapper = new ObjectMapper();
//            return mapper.readValue(o.toString(),taskApiResponseClass);
            return o;
        } catch (Exception e) {
            log.error("Exception occurred while fetching data from redis...",e);
            throw new RuntimeException(e);
        }
    }

    public void set(String keyName,Object o, Long ttl){
        try {
             redisTemplate.opsForValue().set(keyName,o.toString(),ttl, TimeUnit.SECONDS);
            return;
        } catch (Exception e) {
            log.error("Exception occurred while fetching data from redis...",e);
            throw new RuntimeException(e);
        }
    }
}
