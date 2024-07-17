package com.JournalOne.JournalOne.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TasksService tasksService;

    @Test
    void testSendMail()
    {
       // redisTemplate.opsForValue().set("email","paracomando.one@gmail.com");

//        Object email = redisTemplate.opsForValue().get("email");
//        Object salary = redisTemplate.opsForValue().get("salaryPerMonth");
        tasksService.getTasksList2();
    }

}
