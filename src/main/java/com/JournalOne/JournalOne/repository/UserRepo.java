package com.JournalOne.JournalOne.repository;

import com.JournalOne.JournalOne.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, ObjectId> {


}

