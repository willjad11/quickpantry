package com.jaden.quickpantry.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.jaden.quickpantry.models.LoginUser;
import com.jaden.quickpantry.models.User;
import com.jaden.quickpantry.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepo;
    
    public User register(User newUser, BindingResult result) {
    	if(!newUser.getPassword().equals(newUser.getConfirm())) {
    	    result.rejectValue("confirm", "Matches", "The Confirm Password must match Password!");
    	}
    	
    	if (userRepo.findByEmail(newUser.getEmail()).isPresent()) {
    	    result.rejectValue("email", "Matches", "That email is unavailable!");
    	}
    	
    	if(result.hasErrors()) {
    	    return null;
    	}
    	String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
    	newUser.setPassword(hashed);
    	return userRepo.save(newUser);
    }
    public User login(LoginUser newLoginObject, BindingResult result) {
    	Optional<User> potentialUser = userRepo.findByEmail(newLoginObject.getEmail());
    	User user = null;
    	if (potentialUser.isPresent()) {
    		user = potentialUser.get();
    		if(!BCrypt.checkpw(newLoginObject.getPassword(), user.getPassword())) {
    		    result.rejectValue("password", "Matches", "Invalid Password!");
    		}
    		return user;
    	}
    	
    	if (!potentialUser.isPresent()) {
    		result.rejectValue("email", "Matches", "No account with that email");
    	}
    	
    	if (result.hasErrors()) {
    		return null;
    	}

    	else {
    		return user;
    	}
    }
}
