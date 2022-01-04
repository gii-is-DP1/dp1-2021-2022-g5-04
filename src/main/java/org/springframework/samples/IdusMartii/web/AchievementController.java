package org.springframework.samples.IdusMartii.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.IdusMartii.model.Achievement;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.samples.IdusMartii.service.AchievementService;
import org.springframework.samples.IdusMartii.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/achievements")
public class AchievementController {
	
	@Autowired
	private AchievementService achievementService;
	@Autowired
	private UserService userService;
	@GetMapping()
	public String listadoLogros(ModelMap modelMap) {
		String vista = "achievements/listadoLogros";
		Iterable<Achievement> achievements = achievementService.findAll();
		String userName = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();


		User user = userService.findUser(userName).orElse(null);
		modelMap.addAttribute("achievements", achievements);
		modelMap.addAttribute("user", user);
		return vista;
	}
}