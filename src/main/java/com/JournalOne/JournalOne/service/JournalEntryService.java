package com.JournalOne.JournalOne.service;

import com.JournalOne.JournalOne.entity.JournalOneEntries;
import com.JournalOne.JournalOne.entity.User;
import com.JournalOne.JournalOne.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UserService userService;

    @Transactional
     public void saveEntry(JournalOneEntries journalOneEntries, String username){
        User user =  userService.findByUserName(username).orElseThrow();
        JournalOneEntries saved = journalEntryRepo.save(journalOneEntries);
        user.getJournalOneEntriesList().add(saved);
        userService.saveUserEntry(user);
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
        userRepo.getJournalOneEntriesList().removeIf(journal->journal.getId().equals(id));
        userService.saveUserEntry(userRepo);
        journalEntryRepo.deleteById(id);
    }
}
