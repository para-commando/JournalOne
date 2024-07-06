package com.JournalOne.JournalOne.service;


import com.JournalOne.JournalOne.entity.User;
import com.JournalOne.JournalOne.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepo userRepo;

    // BCryptPasswordEncoder is just one implementation of PasswordEncoder
    private static final PasswordEncoder passwordEncoder  = new BCryptPasswordEncoder();

    public boolean saveNewEntry(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepo.save(user);
            return true;
        }catch (Exception e)
        {
            return false;
        }

    }
    public boolean saveUser(User user){
        userRepo.save(user);
        return true;
    }
    public boolean saveNewAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepo.save(user);
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

    public Optional<User> findByUserName(String userName)
    {
        return userRepo.findByUserName(userName);
    }
}
