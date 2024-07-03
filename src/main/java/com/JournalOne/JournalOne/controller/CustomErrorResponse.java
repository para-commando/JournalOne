package com.JournalOne.JournalOne.controller;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
class CustomErrorResponse {
    private String message;
    private int status;
    private String error;

}
