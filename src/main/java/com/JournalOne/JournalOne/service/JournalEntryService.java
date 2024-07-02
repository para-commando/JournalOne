package com.JournalOne.JournalOne.service;

import com.JournalOne.JournalOne.entity.JournalOneEntries;
import com.JournalOne.JournalOne.repository.JournalEntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
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
}
