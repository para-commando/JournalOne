package com.JournalOne.JournalOne.controller;

import com.JournalOne.JournalOne.entity.JournalOneEntries;
import com.JournalOne.JournalOne.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journals")
public class JournalOneEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;
    private Map<Long, JournalOneEntries> journalOneEntriesMap = new HashMap<>();

    // Methods inside a controller class should be public so that they can be accessed and invoked by the spring framework or external http requests
    @GetMapping("/get-all-journals")
    public List<JournalOneEntries> getAllJournals(){
        return new ArrayList<>(journalOneEntriesMap.values());
    }

    @PostMapping("/create-journal")
    public boolean createJournal(@RequestBody JournalOneEntries journalOneEntry){
      //  adding @RequestBody is like asking the spring to take data from request and then turn it into a java object that can be used in the code
//        System.out.println("journalOneEntry "+journalOneEntry);
//        journalOneEntriesMap.put(journalOneEntry.getId(), journalOneEntry);
        journalEntryService.saveEntry(journalOneEntry);
        return true;
    }
    @GetMapping("/get-element-by-id/{journalId}")
    public JournalOneEntries getJournalById(@PathVariable("journalId") Long journalId){

       return null;
    }

    @DeleteMapping("/get-element-by-id/{journalId}")
    public JournalOneEntries deleteJournalById(@PathVariable("journalId") Long journalId){
            return null;
    }
    @PutMapping("/update-element-by-id/{journalId}")
    public boolean updateJournalById(@PathVariable("journalId") Long journalId, @RequestBody JournalOneEntries journalOneEntry ){
       // journalOneEntriesMap.put(journalId,journalOneEntry);
        return true;
    }
}
