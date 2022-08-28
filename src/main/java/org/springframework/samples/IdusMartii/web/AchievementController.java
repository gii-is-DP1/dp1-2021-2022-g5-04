package org.springframework.samples.idusmartii.web;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.idusmartii.model.Achievement;
import org.springframework.samples.idusmartii.model.User;
import org.springframework.samples.idusmartii.service.AchievementService;
import org.springframework.samples.idusmartii.service.AuthoritiesService;
import org.springframework.samples.idusmartii.service.CurrentUserService;
import org.springframework.samples.idusmartii.service.UserService;
import org.springframework.samples.idusmartii.validator.AchievementValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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
	private UserService userService;
	@Autowired
	private AuthoritiesService authoritiesService;
	@Autowired
	private CurrentUserService currentUserService;

	@InitBinder("achievement")
	public void initMatchBinder(WebDataBinder dataBinder){
		dataBinder.setValidator(new AchievementValidator());
	}


	@GetMapping()
	public String listadoLogros(ModelMap modelMap) {
		log.info("Llamando al listado de logros...");
		String vista = "achievements/listadoLogros";
		log.info("Llamada al servicio de logros por el metodo findAll()");
		Iterable<Achievement> achievements = achievementService.findAll();
		
		log.info("Llamada al servicio de usuarios por el método  findUser()");
		User user = userService.findUser(currentUserService.showCurrentUser()).get();
		for(Achievement a: achievements){
			if(!user.getAchievements().contains(a) && a.getAchievementType().getName().equals("ganadas") && achievementService.checkAchievementGanadas(user, (double) a.getValor())){
				achievementService.saveAchievementUser(user.getUsername(), a.getId());
			}
			if(!user.getAchievements().contains(a) && a.getAchievementType().getName().equals("jugadas") && achievementService.checkAchievementJugadas(user, a.getValor())){
				achievementService.saveAchievementUser(user.getUsername(), a.getId());
			}
		}
		modelMap.addAttribute("achievements", achievements);
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("admin", authoritiesService.getAuthorities(user.getUsername()));
		return vista;
	
		
		
	}
	
	@GetMapping(path="/statistics")
	public String listadoStatistics(ModelMap modelMap) {
		String vista = "achievements/listadoEstadisticas";
			
		User user = userService.findUser(currentUserService.showCurrentUser()).get();
		modelMap.addAttribute("statistics", achievementService.listStatistics(user));
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("admin", authoritiesService.getAuthorities(user.getUsername()));
		return vista;	
	}
	
	@GetMapping(path="/ranking")
	public String rankingStatistics(ModelMap modelMap) {
		String vista = "achievements/ranking";
		//User user = userService.findUser(currentUserService.showCurrentUser()).get();
		List<String> rankingWinners = achievementService.ranking();
		List<Long> rankingStats = achievementService.rankingStatistics();
		
		modelMap.addAttribute("winners", rankingWinners);
		modelMap.addAttribute("stats", rankingStats);

		//modelMap.addAttribute("user", user);
		modelMap.addAttribute("admin", true);
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
	
	@GetMapping(path="/new")
	public String editarLogros(ModelMap modelMap ) {
		String vista = "achievements/editarLogro";
		Achievement ac =new Achievement();
		ac.setId(achievementService.nextId()+1);
		modelMap.addAttribute("achievement",ac);
		modelMap.addAttribute("achievementType", achievementService.getAllAchievementsTypes());

		
		return vista;
	

		
	}
	@PostMapping(path="/save")
	public String guardarLogros(ModelMap modelMap, @Valid Achievement achievement ,BindingResult result) {
		String vista = "achievements/editarLogro";
		String currentUser = currentUserService.showCurrentUser();
		User user = userService.findUser(currentUser).get();
		modelMap.addAttribute("admin", userService.isAdmin(user));
		modelMap.addAttribute("achievement",achievement);
			modelMap.addAttribute("achievementType", achievementService.getAllAchievementsTypes());
		log.info("Comprobando si hay errores");
		if (result.hasErrors()){
			log.error("Error encontrado");
			if(result.getFieldError("valor").getDefaultMessage().contains("Failed to convert property value")){
				modelMap.addAttribute("message", "El atributo valor debe de ser un número mayor que 1");
    			return "/exception";
			}
			
			return vista;
		}
		else {
			log.info("No se encontraron errores.");
			log.info("Guardando logro...");
			achievementService.saveAchievement(achievement);
			vista = "redirect:/achievements";
			return vista;
		}
	}
	
	
	
	
	
}