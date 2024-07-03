package com.JournalOne.JournalOne.repository;

import com.JournalOne.JournalOne.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<User, ObjectId> {

        Optional<User> findByUserName(String username);
}

