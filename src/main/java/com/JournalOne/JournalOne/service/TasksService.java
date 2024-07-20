package com.JournalOne.JournalOne.service;

import com.JournalOne.JournalOne.api.response.TaskApiResponseV2;
import com.JournalOne.JournalOne.api.response.TasksApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

// declaring it as a service is same as declaring @Component but tells the reader that this class contains the business logic necessary
@Service
@Slf4j
public class TasksService {

    // below one can be used as a value from secrets for now like env variables
    @Value("${my_api_key}")
    private String apiKeyOne;

    @Value("${my.api.key}")
    private String apiKeyTwo;
    private static final String apiUrl="https://jsonplaceholder.typicode.com/todos/1";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;
    public TasksApiResponse getTasksList2(){

        TasksApiResponse priorityOfTask = redisService.get("taskResponse", TasksApiResponse.class);
        final ResponseEntity<TasksApiResponse> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, TasksApiResponse.class);
        TasksApiResponse tasksApiResponseRet = response.getBody();
        if(priorityOfTask !=null)
        {
            log.info("data fetched from redis successfully");
        } else {
            redisService.set("taskResponse",tasksApiResponseRet, 300L);
        }

        return tasksApiResponseRet;
    }
public List<TasksApiResponse> getTasksList() {
    String url = "https://jsonplaceholder.typicode.com/todos/";
    TasksApiResponse[] tasks = restTemplate.getForObject(url, TasksApiResponse[].class);
    return Arrays.asList(tasks);
}
    public TaskApiResponseV2 createTask(){
        String reqBody = """
                {
                    "title": "foo",
                    "body": "bar",
                    "userId": 1
                }""";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> httpEntity = new HttpEntity<>(reqBody, headers);
        String url = "https://jsonplaceholder.typicode.com/posts";

        ResponseEntity<TaskApiResponseV2> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, TaskApiResponseV2.class);

        return response.getBody();
    }
}
