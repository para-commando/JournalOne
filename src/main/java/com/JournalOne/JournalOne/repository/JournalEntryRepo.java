package com.JournalOne.JournalOne.repository;

import com.JournalOne.JournalOne.entity.JournalOneEntries;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepo extends MongoRepository<JournalOneEntries, ObjectId> {


}

