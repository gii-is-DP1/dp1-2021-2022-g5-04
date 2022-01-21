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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.samples.IdusMartii.model.Achievement;
import org.springframework.samples.IdusMartii.model.Player;
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
import org.springframework.web.bind.annotation.RequestParam;

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
	public String listadoUsuarios(@RequestParam("page") int page, ModelMap modelMap) {
		String vista = "users/listadoUsuarios";
		Pageable completePageable = PageRequest.of(0, 999999999, Sort.by("username"));
		Pageable pageable = PageRequest.of(page-1, 5, Sort.by("username"));
		Page<User> completeUserPage = userService.findAllUsersWithPagination(completePageable);
		Page<User> userPage =  userService.findAllUsersWithPagination(pageable);
		modelMap.addAttribute("users", userPage.getContent());
    	modelMap.addAttribute("admin", userService.isAdmin(userService.findbyUsername(currentUserService.showCurrentUser())));

        modelMap.addAttribute("user", currentUserService.showCurrentUser());
        modelMap.addAttribute("numberOfPagesList", userService.createNumberOfPagesList(completeUserPage, page));
		return vista;
	}   
    
    @GetMapping(path="/own")
   	public String listadoUsuarioPropio(ModelMap modelMap) {
   		String vista = "users/miUsuario";
   		User user =  userService.findbyUsername(currentUserService.showCurrentUser());
   		List<User> users = new ArrayList<>();
   		users.add(user);
   		modelMap.addAttribute("users", users);
    	modelMap.addAttribute("admin", userService.isAdmin(user));
   		modelMap.addAttribute("user", user);
   	//	modelMap.addAttribute("admin", authoritiesService.getAuthorities(user.getUsername()));
   		return vista;
   	}   
    
    @GetMapping(path="/friends")
	public String listadoAmigos(ModelMap modelMap) {
		String vista = "users/listadoAmigos";
		User user = userService.findUser(currentUserService.showCurrentUser()).get();
    	modelMap.addAttribute("admin", userService.isAdmin(user));
		List<User> friends =  userService.findFriends(user.getUsername());
		modelMap.addAttribute("friends", friends);
		return vista;
	}  
    
    @GetMapping(path="/new")
    public String crearUsuario(ModelMap modelMap) {
        String vista = "users/crearUsuario";
        modelMap.addAttribute("user", new User());
        return vista;
    }
    
    @GetMapping(path="/find")
    public String buscarUsuarios(ModelMap modelMap) {
    	String vista = "users/buscarUsuario";
    	Player playerAux = new Player();
    	User user = userService.findUser(currentUserService.showCurrentUser()).get();
    	modelMap.addAttribute("admin", userService.isAdmin(user));
    	modelMap.addAttribute("playerAux", playerAux);
    	return vista;
    }
    
    @PostMapping(path="/find")
    public String buscarUsuariosConUnTexto(@Valid Player player, BindingResult result, ModelMap modelMap) {
    	User current = userService.findUser(currentUserService.showCurrentUser()).get();
    	modelMap.addAttribute("admin", userService.isAdmin(current));
        modelMap.addAttribute("users", userService.findUsersByText(player.getName()));
        modelMap.addAttribute("current", current);
        return "users/usuariosEncontrados";
    }
    	
    
    @GetMapping(path="/{username}/edit")
    public String editarUsuario(@PathVariable("username") String username, ModelMap modelMap) {
        String vista = "users/editarUsuario";
        modelMap.addAttribute("user", userService.findbyUsername(username));
        return vista;
    }
    
    @GetMapping(path="/{username}/edit/own")
    public String editarUsuarioPropio(@PathVariable("username") String username, ModelMap modelMap) {
    	String vista = "users/editarUsuario";
    	User user = userService.findbyUsername(username);
    	modelMap.addAttribute("user", user);
    	modelMap.addAttribute("admin", userService.isAdmin(user));
    	return vista;
    }
    @PostMapping(path="/save")
    public String guardarUsuario(@Valid User user, BindingResult result, ModelMap modelMap) {
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
    
    @PostMapping(path="/{username}/save")
    public String guardarUsuarioModificado(@Valid User user, BindingResult result, @PathVariable("username") String username, ModelMap modelMap) {
        User u = userService.findbyUsername(username);
        u.setEmail(user.getEmail());
        u.setPassword(user.getPassword());
        userService.saveUser(u);
        authoritiesService.saveAuthorities(user.getUsername(), "user");
        modelMap.addAttribute("message", "¡Usuario modificado correctamente!");
        return "redirect:/";
    }
    
    @PostMapping(path="/{username}/save/own")
    public String guardarUsuarioPropioModificado(@Valid User user, BindingResult result, @PathVariable("username") String username, ModelMap modelMap) {
        User u = userService.findbyUsername(username);
        u.setEmail(user.getEmail());
        u.setPassword(user.getPassword());
        userService.saveUser(u);
        authoritiesService.saveAuthorities(user.getUsername(), "user");
        modelMap.addAttribute("message", "¡Usuario modificado correctamente!");
        return "redirect:/";
    }
  
    @GetMapping(path="/delete/{username}")
    public String eliminarAmigo(@PathVariable("username") String username, ModelMap modelMap) {
    	User currentUser = userService.findbyUsername(currentUserService.showCurrentUser());
    	userService.deleteFriend(currentUser, username);
    	return "redirect:/users/friends";
    }
    
    @GetMapping(path="/{username}/delete")
    public String eliminarUserIntermedio(@PathVariable("username") String username, ModelMap modelMap) {
    	userService.delete(userService.findbyUsername(username));
    	return "redirect:/users?page=1";
    }
    
    
   
}