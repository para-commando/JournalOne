package com.JournalOne.JournalOne.service;


import com.JournalOne.JournalOne.entity.User;
import com.JournalOne.JournalOne.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public boolean saveUserEntry(User users){
        userRepo.save(users);
        return true;
    }

    public List<User> getAllUsers()
    {
       return userRepo.findAll();
    }

    // optional is like box which may or may not contain values
    public Optional<User> findUserById(ObjectId id)
    {
        return userRepo.findById(id);
    }

    public boolean deleteUserById(ObjectId id)
    {
        userRepo.deleteById(id);
        return true;
    }
}
