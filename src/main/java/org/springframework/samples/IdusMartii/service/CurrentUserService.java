package org.springframework.samples.IdusMartii.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
	
    public String showCurrentUser(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        User currentUser =(User)authentication.getPrincipal();
        String username = currentUser.getUsername();
        System.out.println(username);

        return username;
       
    }
}
