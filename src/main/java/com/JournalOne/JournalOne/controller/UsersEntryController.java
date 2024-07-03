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


@RestController
@RequestMapping("/users")
public class UsersEntryController {
    @Autowired
    private UserService userService;
    @Autowired
    private CustomErrorResponse customErrorResponseInternalServerError;
    @Autowired
    private CustomErrorResponse customErrorResponseNotFoundError;

    // private Map<St, User> userMap = new HashMap<>();
    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<?> userDetails = userService.getAllUsers();
//            throw new Exception(); // Simulate an error for demonstration
            if (userDetails != null) {
                return new ResponseEntity<>(userDetails, HttpStatus.OK);
            }
            customErrorResponseNotFoundError.setError("No data found");
            customErrorResponseNotFoundError.setMessage("No data found");
            customErrorResponseNotFoundError.setStatus(HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(customErrorResponseNotFoundError);
        } catch (Exception e) {

            customErrorResponseInternalServerError.setError("An error");
            customErrorResponseInternalServerError.setMessage("An error message");
            customErrorResponseInternalServerError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(customErrorResponseInternalServerError);
        }
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            boolean userDetails = userService.saveUserEntry(user);
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

    @PutMapping("/update-user-by-username/{username}")
    public ResponseEntity<?> updateUser(@PathVariable("username") String userName, @RequestBody User user) {
        try {
            boolean isValidUserDetails = user.getUserName() != null && !user.getUserName().isEmpty();
            if (isValidUserDetails) {
                User user1 = userService.findByUserName(userName).orElse(null);
                if (user1 != null) {
                    user1.setUserName(!Objects.equals(user1.getUserName(), user.getUserName()) ? user.getUserName() : user1.getUserName());
                    user1.setPassword(!Objects.equals(user1.getPassword(), user.getPassword()) ? user.getPassword() : user1.getPassword());
                    boolean userDetails = userService.saveUserEntry(user1);

                }
                return new ResponseEntity<>(user, HttpStatus.OK);
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
    @GetMapping("/get-user-by-username/{username}")
    public ResponseEntity<?> getUserById(@PathVariable("username") String username ) {
        try {
            User userData= userService.findByUserName(username).orElse(null);
            if (userData !=null) {
                return new ResponseEntity<>(userData, HttpStatus.OK);
            }
            customErrorResponseNotFoundError.setError("User not found");
            customErrorResponseNotFoundError.setMessage("User data not found");
            customErrorResponseNotFoundError.setStatus(HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
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
    @DeleteMapping("/delete-user-by-id/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable("userId") ObjectId userId ) {
        try {
            boolean userData= userService.deleteUserById(userId) ;
            if (userData) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            customErrorResponseNotFoundError.setError("User deletion failed");
            customErrorResponseNotFoundError.setMessage("User deletion found");
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
