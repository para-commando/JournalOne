package com.JournalOne.JournalOne.controller;

import com.JournalOne.JournalOne.entity.User;
import com.JournalOne.JournalOne.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private CustomErrorResponse customErrorResponseInternalServerError;
    @Autowired
    private CustomErrorResponse customErrorResponseNotFoundError;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> all = userService.getAllUsers();
            if (all != null & !all.isEmpty()) {
                return new ResponseEntity<>(all, HttpStatus.OK);
            }
            customErrorResponseNotFoundError.setError("Invalid User data");
            customErrorResponseNotFoundError.setMessage("User data invalid, please rectify");
            customErrorResponseNotFoundError.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
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

    @PostMapping("/create-admin-user")
    public ResponseEntity<?> createAdminUser(@RequestBody User adminUser) {
        try {
            boolean result = userService.saveNewAdmin(adminUser);
            if (result) {
                return new ResponseEntity<>(adminUser, HttpStatus.OK);
            }
            customErrorResponseNotFoundError.setError("Admin user creation failed");
            customErrorResponseNotFoundError.setMessage("Admin user creation failed, please try again");
            customErrorResponseNotFoundError.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
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
