/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.IdusMartii.web;


import java.io.Console;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.IdusMartii.model.Achievement;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.samples.IdusMartii.service.AuthoritiesService;
import org.springframework.samples.IdusMartii.service.CurrentUserService;
import org.springframework.samples.IdusMartii.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private  AuthoritiesService authoritiesService;
    @Autowired
	private CurrentUserService currentUserService;
    
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    } 
    
    @GetMapping()
	public String listadoUsuario(ModelMap modelMap) {
		String vista = "users/listadoUsuarios";
	
		Iterable< User> users =  userService.findAll();
	
		modelMap.addAttribute("users", users);
        modelMap.addAttribute("usert", currentUserService.showCurrentUser())
		return vista;
	}   
    @GetMapping(path="/friends")
	public String listadoAmigos(ModelMap modelMap) {
		String vista = "users/listadoAmigos";
		if(authoritiesService.getAuthorities(currentUserService.showCurrentUser())) {
            modelMap.addAttribute("admin", true);
    	}else {
            modelMap.addAttribute("admin", false);
    	}
		List<User> friends =  userService.findFriends(currentUserService.showCurrentUser());
		modelMap.addAttribute("friends", friends);
		return vista;
	}  
    
    @GetMapping(path="/new")
    public String crearJugador(ModelMap modelMap) {
        String vista = "users/crearUsuario";
        modelMap.addAttribute("user", new User());
        return vista;
    }
    
    @GetMapping(path="/{id}/edit")
    public String editarJugador(ModelMap modelMap, @PathVariable  String id ) {
        String vista = "users/editarUsuario";
        
      
        modelMap.addAttribute("user", userService.findbyUsername(id));
        return vista;
    }

    @PostMapping(path="/save")
    public String guardarJugador(@Valid User user, BindingResult result, ModelMap modelMap) {
        if (result.hasErrors()) {
            return "users/crearUsuario";
        } else {
            modelMap.addAttribute("users", user);
            userService.saveUser(user);
            authoritiesService.saveAuthorities(user.getUsername(), "user");
            modelMap.addAttribute("message", "¡Usuario guardado correctamente!");
        }
        return "redirect:/";
    }
    
    @PostMapping(path="/{id}/save")
    public String guardarJugadorModificado(@Valid User user, BindingResult result, ModelMap modelMap, @PathVariable("id") String id) {
       
        	User u = userService.findbyUsername(id);
        
        
        	u.setEmail(user.getEmail());
        	u.setPassword(user.getPassword());
    		userService.saveUser(u);
            authoritiesService.saveAuthorities(user.getUsername(), "user");
            modelMap.addAttribute("message", "¡Usuario guardado correctamente!");
        
        return "redirect:/users";
    }
  
    @PostMapping(path="/delete/{username}")
    public String eliminarAmigo(@PathVariable("username") String username, ModelMap modelMap) {
    	User currentUser = userService.findbyUsername(currentUserService.showCurrentUser());
    	userService.deleteFriend(currentUser, username);
    	return "redirect:/users/friends";
    }
}