package com.JournalOne.JournalOne.service;


import com.JournalOne.JournalOne.entity.User;
import com.JournalOne.JournalOne.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
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
            log.info("this is an info from saveNewEntry");
            log.warn("this is an warning from saveNewEntry");
            log.error("Error occurred for user {} :",user.getUserName(),e);
            // trace and debug will not be enabled by default hence would need to manually enable it
            log.trace("this is an trace from saveNewEntry");
            log.debug("this is an debug from saveNewEntry");
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
