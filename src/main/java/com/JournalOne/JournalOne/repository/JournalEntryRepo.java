package com.JournalOne.JournalOne.repository;

import com.JournalOne.JournalOne.entity.JournalOneEntries;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepo extends MongoRepository<JournalOneEntries,String > {


}

