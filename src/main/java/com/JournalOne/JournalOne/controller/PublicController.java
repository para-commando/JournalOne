package com.JournalOne.JournalOne.controller;

import com.JournalOne.JournalOne.entity.User;
import com.JournalOne.JournalOne.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;
    @Autowired
    private CustomErrorResponse customErrorResponseInternalServerError;
    @Autowired
    private CustomErrorResponse customErrorResponseNotFoundError;
    @GetMapping("/health-check")
    public String healthCheck(){
        return "success";
    }
    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {

            boolean userDetails = userService.saveNewEntry(user);
//            throw new Exception(); // Simulate an error for demonstration
            if (userDetails) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            customErrorResponseNotFoundError.setError("User data creation failed");
            customErrorResponseNotFoundError.setMessage("User data creation failed, please try again");
            customErrorResponseNotFoundError.setStatus(HttpStatus.NOT_IMPLEMENTED.value());
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(customErrorResponseNotFoundError);
        } catch (Exception e) {

            customErrorResponseInternalServerError.setError("Internal Server Error");
            customErrorResponseInternalServerError.setMessage("Internal Server Error Occurred, please try again");
            customErrorResponseInternalServerError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(customErrorResponseInternalServerError);
        }
    }

}
