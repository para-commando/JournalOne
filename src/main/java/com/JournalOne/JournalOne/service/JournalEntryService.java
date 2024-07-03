package com.JournalOne.JournalOne.service;

import com.JournalOne.JournalOne.entity.JournalOneEntries;
import com.JournalOne.JournalOne.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepo journalEntryRepo;

    public void saveEntry(JournalOneEntries journalOneEntries){
        journalEntryRepo.save(journalOneEntries);
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

    public void deleteElementById(ObjectId id)
    {
        journalEntryRepo.deleteById(id);
    }
}
