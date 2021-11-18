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
package org.springframework.samples.IdusMartii.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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
	
	@InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }
	
	@GetMapping()
	public String listadoJugadores(ModelMap modelMap) {
		String vista = "users/listadoUsuarios";
		Iterable<User> users = userService.findAll();
		modelMap.addAttribute("users", users);
		return vista;
	}
	
	@GetMapping(path="/new")
	public String crearJugador(ModelMap modelMap) {
		String vista = "users/createUserForm";
		modelMap.addAttribute("user", new User());
		return vista;
	}

	@PostMapping(path="/save")
	public String guardarJugador(@Valid User user, BindingResult result, ModelMap modelMap) {
		String vista = "users/listadoUsuarios";
		if (result.hasErrors()) {
			modelMap.addAttribute("users", user);
			return "users/createUserForm";
		} else {
			userService.saveUser(user);
			modelMap.addAttribute("message", "¡Usuario guardado correctamente!");
		}
		return vista;
	}

}
