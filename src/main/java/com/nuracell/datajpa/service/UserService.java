package com.nuracell.datajpa.service;

import com.nuracell.datajpa.entity.User;
import com.nuracell.datajpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public User getUserByUsername(String userName) throws Exception{
        return userRepository.findUserByUsername(userName);
    }

    public boolean checkUserPresence(User user) throws Exception {
        return userRepository.findUserByUsername(user.getUsername()) != null;
    }


}
