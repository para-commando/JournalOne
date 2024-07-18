package com.JournalOne.JournalOne.service;


import com.JournalOne.JournalOne.config.AppCache;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RedisTests {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private TasksService tasksService;


    @Test
    void testSendMail() {
        // redisTemplate.opsForValue().set("email","paracomando.one@gmail.com");

//        Object email = redisTemplate.opsForValue().get("email");
//        Object salary = redisTemplate.opsForValue().get("salaryPerMonth");
        assertNotNull(tasksService.getTasksList2());
    }

}
