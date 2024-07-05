package com.JournalOne.JournalOne.service;

import com.JournalOne.JournalOne.entity.User;
import com.JournalOne.JournalOne.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepo.findByUserName(username).orElse(null);
        if(user !=null){
//   new String[0] is a way to specify the type of the array. It could be any size, but 0 is commonly used for efficiency.
//   Internally, Java will create a new array of the correct size (length of the list) and copy the elements into it.
            return org.springframework.security.core.userdetails.User.builder().username(user.getUserName()).password(user.getPassword()).roles(user.getRoles().toArray(new String[0])).build();

        }

        throw new UsernameNotFoundException("Username not found with username"+username);

    }
}
