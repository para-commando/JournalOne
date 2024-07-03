package com.JournalOne.JournalOne.utils;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CustomErrorResponse {
    private String message;
    private int status;
    private String error;

}
