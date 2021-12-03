package org.springframework.samples.IdusMartii.web;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
// import org.springframework.samples.IdusMartii.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CurrentUserController {
    //@GetMapping("/currentuser")
    public String showCurrentUser(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        
        if(authentication!=null)
            if(authentication.isAuthenticated() && authentication.getPrincipal() instanceof User){
                User currentUser =(User)authentication.getPrincipal();
                String username = currentUser.getUsername();
                System.out.println(username);
                
            }else
                System.out.println("El usuario no ha iniciado sesión");
       
        return "welcome";
    }
    
}
 