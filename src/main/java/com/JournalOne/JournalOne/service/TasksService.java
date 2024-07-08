package com.JournalOne.JournalOne.service;

import com.JournalOne.JournalOne.api.response.TasksApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class TasksService {
    private static final String apiKey="c332c4ea21bb1ce39af830288b91c69b";

    private static final String apiUrl="https://jsonplaceholder.typicode.com/todos/1";

    @Autowired
    private RestTemplate restTemplate;
    public TasksApiResponse getTasksList2(){

        final ResponseEntity<TasksApiResponse> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, TasksApiResponse.class);
        TasksApiResponse tasksApiResponse = response.getBody();
        return tasksApiResponse;
    }
public List<TasksApiResponse> getTasksList() {
    String url = "https://jsonplaceholder.typicode.com/todos/";
    TasksApiResponse[] tasks = restTemplate.getForObject(url, TasksApiResponse[].class);
    return Arrays.asList(tasks);
}
}
