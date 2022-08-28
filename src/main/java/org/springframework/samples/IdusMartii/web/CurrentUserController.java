package org.springframework.samples.idusmartii.web;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
// import org.springframework.samples.IdusMartii.model.User;
import org.springframework.stereotype.Controller;

@Controller
public class CurrentUserController {

    public String showCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() == "anonymousUser") {
        	return "anonymous"; 
        } else {
        	User currentUser =(User)authentication.getPrincipal();
            String username = currentUser.getUsername();
            return username;
        }
    }
}
 