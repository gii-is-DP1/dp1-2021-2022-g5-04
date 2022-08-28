package org.springframework.samples.idusmartii.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CurrentUserService {
	
    public String showCurrentUser() {
    	log.info("Comprobando usuario actual...");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() == "anonymousUser") {
        	return "anonymous";
        } else {
        	 User currentUser = (User) authentication.getPrincipal();
             String username = currentUser.getUsername();
             
             return username;
        }
    }
}
