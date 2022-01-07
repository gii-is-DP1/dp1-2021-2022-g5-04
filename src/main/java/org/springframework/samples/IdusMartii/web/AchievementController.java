package org.springframework.samples.IdusMartii.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.IdusMartii.model.Achievement;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.samples.IdusMartii.service.AchievementService;
import org.springframework.samples.IdusMartii.service.AuthoritiesService;
import org.springframework.samples.IdusMartii.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/achievements")
public class AchievementController {
	
	@Autowired
	private AchievementService achievementService;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthoritiesService authoritiesService;
	@GetMapping()
	public String listadoLogros(ModelMap modelMap) {
		String vista = "achievements/listadoLogros";
		Iterable<Achievement> achievements = achievementService.findAll();
		String userName = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		User user = userService.findUser(userName).orElse(null);
		modelMap.addAttribute("achievements", achievements);
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("admin", authoritiesService.getAuthorities(user.getUsername()));
		return vista;
	
		
		
	}
	
	@GetMapping(path="/{id}/edit")
	public String nuevoLogros(ModelMap modelMap , @PathVariable("id") int id) {
		String vista = "achievements/editarLogro";
		  modelMap.addAttribute("achievement", achievementService.findById(id));
		
		return vista;
	
		
		
	}
	
	@GetMapping(path="/new")
	public String editarLogros(ModelMap modelMap ) {
		String vista = "achievements/editarLogro";
		Achievement ac =new Achievement();
		ac.setId(achievementService.nextId()+1);
		  modelMap.addAttribute("achievement",ac);
		
		return vista;
	
		
		
	}
	@PostMapping(path="/{id}/save")
	public String guardarLogros(ModelMap modelMap, @Valid Achievement achievement , @PathVariable("id") int id) {
		Achievement ac = achievement;
		ac.setId(id);
		achievementService.saveAchievement(ac);
		
		String vista = "redirect:/achievements";
		
		return vista;
	
		
		
	}
	
	
	
	
	
}