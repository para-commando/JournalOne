package com.JournalOne.JournalOne.controller;

import com.JournalOne.JournalOne.entity.JournalOneEntries;
import com.JournalOne.JournalOne.entity.User;
import com.JournalOne.JournalOne.service.JournalEntryService;
import com.JournalOne.JournalOne.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
  class CustomErrorResponse {
    private String message;
    private int status;
    private String error;

    public CustomErrorResponse(String message, int status, String error) {
        this.message = message;
        this.status = status;
        this.error = error;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

@RestController
@RequestMapping("/users")
public class UsersEntryController {
    @Autowired
    private UserService userService;
   // private Map<St, User> userMap = new HashMap<>();
    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<?> userDetails = userService.getAllUsers();
            throw new Exception(); // Simulate an error for demonstration
            // if (userDetails != null) {
            //     return new ResponseEntity<>(userDetails, HttpStatus.OK);
            // }
            // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            CustomErrorResponse errorResponse = new CustomErrorResponse("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorResponse);
        }
    }


}
