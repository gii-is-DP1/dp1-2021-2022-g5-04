package org.springframework.samples.idusmartii.web;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idusmartii.model.User;
import org.springframework.samples.idusmartii.service.AuthoritiesService;
import org.springframework.samples.idusmartii.service.CurrentUserService;
import org.springframework.samples.idusmartii.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WelcomeController {
	@Autowired
	private CurrentUserService currentUserService;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthoritiesService authoritiesService;
	
    @GetMapping({"/","/welcome"})
    public String welcome(Map<String, Object> model, HttpServletResponse response) {
    	if (currentUserService.showCurrentUser() != "anonymous") {
    		User current = userService.findUser(currentUserService.showCurrentUser()).get();
    		model.put("admin", authoritiesService.getAuthorities(current.getUsername()));
    	} else {
    		model.put("admin", false);
    	}
        response.addHeader("Refresh","30"); 
        model.put("people", userService.crearAlumnos());
        model.put("title", "Idus Martii"); 
        model.put("group", "L5-4");
        
        return "welcome";
        
      }
}