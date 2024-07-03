package com.JournalOne.JournalOne.controller;

import com.JournalOne.JournalOne.entity.JournalOneEntries;
import com.JournalOne.JournalOne.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journals")
public class JournalOneEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;
    private Map<Long, JournalOneEntries> journalOneEntriesMap = new HashMap<>();

    // Methods inside a controller class should be public so that they can be accessed and invoked by the spring framework or external http requests
    @GetMapping("/get-all-journals")
    public ResponseEntity<?> getAllJournals() {
        List<?> allRecords = journalEntryService.getAll();
        if (allRecords != null && !allRecords.isEmpty()) {
            return new ResponseEntity<>(allRecords, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-journal")
    public ResponseEntity<JournalOneEntries> createJournal(@RequestBody JournalOneEntries journalOneEntry) {
        //  adding @RequestBody is like asking the spring to take data from request and then turn it into a java object that can be used in the code

        // calling setter method for the class variable Date
        journalOneEntry.setDate(LocalDateTime.now());
        try {
            journalEntryService.saveEntry(journalOneEntry);
            return new ResponseEntity<>(journalOneEntry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
        journalEntryService.deleteElementById(journalId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update-element-by-id/{journalId}")
    public ResponseEntity<JournalOneEntries> updateJournalById(@PathVariable("journalId") ObjectId journalId, @RequestBody JournalOneEntries journalOneEntry) {
        // journalOneEntriesMap.put(journalId,journalOneEntry);
        JournalOneEntries prevJournalEntry = journalEntryService.findElementById(journalId).orElse(null);
        if (prevJournalEntry != null) {
            prevJournalEntry.setTitle(journalOneEntry.getTitle() != null && !journalOneEntry.getTitle().isEmpty() && !Objects.equals(journalOneEntry.getTitle(), prevJournalEntry.getTitle()) ? journalOneEntry.getTitle() : prevJournalEntry.getTitle());
            prevJournalEntry.setContent(journalOneEntry.getContent() != null && !journalOneEntry.getContent().isEmpty() && !Objects.equals(journalOneEntry.getContent(), prevJournalEntry.getContent()) ? journalOneEntry.getContent() : prevJournalEntry.getContent());
            journalEntryService.saveEntry(prevJournalEntry);
            return new ResponseEntity<>(prevJournalEntry, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
