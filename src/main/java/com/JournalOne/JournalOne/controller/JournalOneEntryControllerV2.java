package com.JournalOne.JournalOne.controller;

import com.JournalOne.JournalOne.entity.JournalOneEntries;
import com.JournalOne.JournalOne.entity.User;
import com.JournalOne.JournalOne.repository.UserRepo;
import com.JournalOne.JournalOne.service.JournalEntryService;
import com.JournalOne.JournalOne.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journals")
public class JournalOneEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;
    @Autowired
    private CustomErrorResponse customErrorResponseNotFoundError;

    @Autowired
    private CustomErrorResponse customErrorResponseInternalServerError;

    // Methods inside a controller class should be public so that they can be accessed and invoked by the spring framework or external http requests
    @GetMapping("/get-all-journals")
    public ResponseEntity<?> getAllJournals() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.findByUserName(username).orElse(null);
            if (user != null) {
                List<JournalOneEntries> allRecords = user.getJournalOneEntriesList();
                if (allRecords != null && !allRecords.isEmpty()) {
                    return new ResponseEntity<>(allRecords, HttpStatus.OK);
                }
            }
            customErrorResponseNotFoundError.setError("Not found");
            customErrorResponseNotFoundError.setMessage("No Journals found for the given username");
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

    @PostMapping("/create-journal")
    @Transactional
    public ResponseEntity<?> createJournal(@RequestBody JournalOneEntries journalOneEntry) {
        //  adding @RequestBody is like asking the spring to take data from request and then turn it into a java object that can be used in the code

        // calling setter method for the class variable Date

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalOneEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(journalOneEntry, username);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Error is" + e);
            customErrorResponseInternalServerError.setError("Internal Server Error");
            customErrorResponseInternalServerError.setMessage("Internal Server Error Occurred, please try again");
            customErrorResponseInternalServerError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(customErrorResponseInternalServerError);
        }


    }

    @GetMapping("/get-element-by-id/{journalId}")
    public ResponseEntity<JournalOneEntries> getJournalById(@PathVariable("journalId") ObjectId journalId) {
//         journalEntryService.findElementById(journalId)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        try {
            Optional<JournalOneEntries> journalOneEntries = journalEntryService.findElementById(journalId);
            if (journalOneEntries.isPresent()) {
                return new ResponseEntity<>(journalOneEntries.get(), HttpStatus.OK);
            }
            ;
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @DeleteMapping("/delete-element-by-id/{journalId}")
    public ResponseEntity<?> deleteJournalById(@PathVariable("journalId") ObjectId journalId) {
        // when used "?" it means that an object of any return type can be sent
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            JournalOneEntries journalOneEntries = journalEntryService.findElementById(journalId).orElse(null);
            if (journalOneEntries != null) {
                journalEntryService.deleteElementById(journalId, username);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            customErrorResponseNotFoundError.setError("Deletion failed");
            customErrorResponseNotFoundError.setMessage("No journal found for the given id");
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

    @PutMapping("/update-element-by-id/{journalId}/{username}")
    public ResponseEntity<?> updateJournalById(@PathVariable("journalId") ObjectId journalId, @PathVariable("username") String username, @RequestBody JournalOneEntries journalOneEntry) {
        // journalOneEntriesMap.put(journalId,journalOneEntry);
        try {
            JournalOneEntries prevJournalEntry = journalEntryService.findElementById(journalId).orElse(null);

            if (prevJournalEntry != null) {
                prevJournalEntry.setTitle(!journalOneEntry.getTitle().isEmpty() && !Objects.equals(journalOneEntry.getTitle(), prevJournalEntry.getTitle()) ? journalOneEntry.getTitle() : prevJournalEntry.getTitle());
                prevJournalEntry.setContent(journalOneEntry.getContent() != null && !journalOneEntry.getContent().isEmpty() && !Objects.equals(journalOneEntry.getContent(), prevJournalEntry.getContent()) ? journalOneEntry.getContent() : prevJournalEntry.getContent());
                journalEntryService.saveEntry(prevJournalEntry, username);
                return new ResponseEntity<>(prevJournalEntry, HttpStatus.OK);
            }
            customErrorResponseNotFoundError.setError("Not found");
            customErrorResponseNotFoundError.setMessage("No Journals or User found for the given data");
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
}
