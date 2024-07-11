package com.JournalOne.JournalOne.repository;

import com.JournalOne.JournalOne.entity.Configs;
import com.JournalOne.JournalOne.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ConfigsRepo extends MongoRepository<Configs, ObjectId> {

}
