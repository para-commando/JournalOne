package com.JournalOne.JournalOne.controller;

import com.JournalOne.JournalOne.entity.JournalOneEntries;
import jakarta.servlet.http.PushBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journals")
public class JournalOneEntryController {
    private Map<Long, JournalOneEntries> journalOneEntriesMap = new HashMap<>();

    // Methods inside a controller class should be public so that they can be accessed and invoked by the spring framework or external http requests
    @GetMapping("/get-all-journals")
    public List<JournalOneEntries> getAllJournals(){
        return new ArrayList<>(journalOneEntriesMap.values());
    }

    @PostMapping("/create-journal")
    public boolean createJournal(@RequestBody JournalOneEntries journalOneEntry){
      //  adding @RequestBody is like asking the spring to take data from request and then turn it into a java object that can be used in the code
        System.out.println("journalOneEntry "+journalOneEntry);
        journalOneEntriesMap.put(journalOneEntry.getId(), journalOneEntry);
        return true;
    }
    @GetMapping("/get-element-by-id/{journalId}")
    public JournalOneEntries getJournalById(@PathVariable("journalId") Long journalId){

       return journalOneEntriesMap.get(journalId);
    }

    @DeleteMapping("/get-element-by-id/{journalId}")
    public JournalOneEntries deleteJournalById(@PathVariable("journalId") Long journalId){
            return journalOneEntriesMap.remove(journalId);
    }
    @PutMapping("/update-element-by-id/{journalId}")
    public boolean updateJournalById(@PathVariable("journalId") Long journalId, @RequestBody JournalOneEntries journalOneEntry ){
        journalOneEntriesMap.put(journalId,journalOneEntry);
        return true;
    }
}
