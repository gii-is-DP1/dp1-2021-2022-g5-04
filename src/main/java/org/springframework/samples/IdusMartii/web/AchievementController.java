package org.springframework.samples.IdusMartii.web;

import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.IdusMartii.model.Achievement;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.samples.IdusMartii.service.AchievementService;
import org.springframework.samples.IdusMartii.service.AchievementUserService;
import org.springframework.samples.IdusMartii.service.AuthoritiesService;
import org.springframework.samples.IdusMartii.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/achievements")
public class AchievementController {
	
	@Autowired
	private AchievementService achievementService;

	@Autowired
	private AchievementUserService achievementUserService;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthoritiesService authoritiesService;
	@GetMapping()
	public String listadoLogros(ModelMap modelMap) {
		log.info("Llamando al listado de logros...");
		String vista = "achievements/listadoLogros";
		log.info("Llamada al servicio de logros por el metodo findAll()");
		Iterable<Achievement> achievements = achievementService.findAll();
		String userName = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		log.info("Llamada al servicio de usuarios por el método  findUser()");
		User user = userService.findUser(userName).orElse(null);
		modelMap.addAttribute("achievements", achievements);
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("admin", authoritiesService.getAuthorities(user.getUsername()));
		return vista;
	
		
		
	}
	
	@GetMapping(path="/statistics")
	public String listadoStatistics(ModelMap modelMap) {
		String vista = "achievements/listadoEstadisticas";
		//Iterable<Achievement> achievements = achievementService.findAll();
		String userName = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		User user = userService.findUser(userName).orElse(null);
		Integer statistics = achievementUserService.listStatistics(user);
		Integer win = user.getVictorias();
		modelMap.addAttribute("statistics", statistics);
if (win!=null) {
		modelMap.addAttribute("win", win);}
else {
	modelMap.addAttribute("win", 0);

}
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("admin", authoritiesService.getAuthorities(user.getUsername()));
		return vista;
	
		
		
	}
	
	@GetMapping(path="/statistics/ranking")
	public String rankingStatistics(ModelMap modelMap) {
		String vista = "achievements/ranking";
		//Iterable<Achievement> achievements = achievementService.findAll();
		String userName = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		User user = userService.findUser(userName).orElse(null);
		Map<Integer, List<String> >statistics = achievementUserService.rankingStatistics();
		
		modelMap.addAttribute("map", statistics);

		modelMap.addAttribute("user", user);
		modelMap.addAttribute("admin", authoritiesService.getAuthorities(user.getUsername()));
		return vista;}
		
		@GetMapping(path="/statistics/rankingWinners")
		public String rankingWinners(ModelMap modelMap) {
			String vista = "achievements/ranking";
			//Iterable<Achievement> achievements = achievementService.findAll();
			String userName = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
			User user = userService.findUser(userName).orElse(null);
			Map<Integer, List<String> >statistics = achievementUserService.rankingWinners();
			
			modelMap.addAttribute("map", statistics);

			modelMap.addAttribute("user", user);
			modelMap.addAttribute("admin", authoritiesService.getAuthorities(user.getUsername()));
			return vista;
		
	
		
		
	}
	
	
	@GetMapping(path="/{id}/edit")
	public String nuevoLogros(ModelMap modelMap , @PathVariable("id") int id) {
		String vista = "achievements/editarLogro";
		log.info("Acceso al servicio de logros por el metodo findById()");
		  modelMap.addAttribute("achievement", achievementService.findById(id));
		log.info("Acceso al servicio de logros por el metodo getAllAchievementsTypes()");
		  modelMap.addAttribute("achievementType", achievementService.getAllAchievementsTypes());
		
		return vista;
	
		
		
	}
	
	
	
		
		
	@GetMapping(path="/{id}/statistics")
	public String listadoEstadisticas(ModelMap modelMap , @PathVariable("id") int id) {
		String vista = "achievements/editarLogro";
		  modelMap.addAttribute("achievement", achievementService.findById(id));
		  modelMap.addAttribute("achievementType", achievementService.getAllAchievementsTypes());
		
		return vista;}
	
	@GetMapping(path="/new")
	public String editarLogros(ModelMap modelMap ) {
		String vista = "achievements/editarLogro";
		Achievement ac =new Achievement();
		ac.setId(achievementService.nextId()+1);
		modelMap.addAttribute("achievement",ac);
		modelMap.addAttribute("achievementType", achievementService.getAllAchievementsTypes());

		
		return vista;
	

		
	}
	@PostMapping(path="/{id}/save")
	public String guardarLogros(ModelMap modelMap, @Valid Achievement achievement ,BindingResult result, @PathVariable("id") int id) {
		String vista = "achievements/editarLogro";
		log.info("Comprobando si hay errores");
		if (result.hasErrors()){
			log.error("Error encontrado");
			modelMap.addAttribute("achievement",achievement);
			modelMap.addAttribute("achievementType", achievementService.getAllAchievementsTypes());
			return vista;
		}
		else {
			log.info("No se encontraron errores.");
			achievement.setId(id);
			log.info("Guardando logro...");
			achievementService.saveAchievement(achievement);
			vista = "redirect:/achievements";
			return vista;
		}
	}
	
	
	
	
	
}