package com.JournalOne.JournalOne.service;

import com.JournalOne.JournalOne.entity.JournalOneEntries;
import com.JournalOne.JournalOne.entity.User;
import com.JournalOne.JournalOne.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    private static final Logger logger= LoggerFactory.getLogger(JournalEntryService.class);
    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UserService userService;

    @Transactional
     public void saveEntry(JournalOneEntries journalOneEntries, String username){
        User user =  userService.findByUserName(username).orElseThrow();
        JournalOneEntries saved = journalEntryRepo.save(journalOneEntries);
        user.getJournalOneEntriesList().add(saved);
        System.out.println("user is"+user);
        userService.saveUser(user);
    }

    public List<JournalOneEntries> getAll()
    {
       return journalEntryRepo.findAll();
    }

    // optional is like box which may or may not contain values
    public Optional<JournalOneEntries> findElementById(ObjectId id)
    {
        return journalEntryRepo.findById(id);
    }

    @Transactional
    public void deleteElementById(ObjectId id,String username)
    {
        User userRepo = userService.findByUserName(username).orElseThrow();
       boolean isRemoved = userRepo.getJournalOneEntriesList().removeIf(journal->journal.getId().equals(id));
       if(isRemoved) {
           userService.saveNewEntry(userRepo);
           journalEntryRepo.deleteById(id);
       }
       else {
           throw new RuntimeException("Journal Deletion failed");
       }
    }
}
