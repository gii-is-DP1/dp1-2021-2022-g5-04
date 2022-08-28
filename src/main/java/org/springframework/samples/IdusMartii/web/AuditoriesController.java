package org.springframework.samples.IdusMartii.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.IdusMartii.model.Achievement;
import org.springframework.samples.IdusMartii.model.Match;
import org.springframework.samples.IdusMartii.model.Player;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.samples.IdusMartii.service.AchievementService;
import org.springframework.samples.IdusMartii.service.MatchService;
import org.springframework.samples.IdusMartii.service.PlayerService;
import org.springframework.samples.IdusMartii.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/auditories")	
public class AuditoriesController {

    @Autowired
    private UserService userService;
	
    @Autowired
    private PlayerService playerService;
    
    @Autowired
    private MatchService matchService;
    
    @Autowired
    private AchievementService AchievementService;
    
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }
    
    @GetMapping()
	public String auditoriasMenu(ModelMap modelMap) {
		String vista = "auditories/auditoriasMenu";
		Iterable<User> users =  userService.findAll();
		modelMap.addAttribute("users", users);
		return vista;
	}
    
    @GetMapping(path="/users")
	public String auditoriasUsuario(ModelMap modelMap) {
		String vista = "auditories/auditoriasUser";
		
		Iterable<User> users =  userService.findAll();
		modelMap.addAttribute("users", users);
		return vista;
	} 
    @GetMapping(path="/players")
	public String auditoriasPlayer(ModelMap modelMap) {
		String vista = "auditories/auditoriasPlayer";
		
		Iterable<Player> players =  playerService.findAll();
		modelMap.addAttribute("players", players);
		return vista;
	}   
    @GetMapping(path="/matches")
	public String auditoriasMatch(ModelMap modelMap) {
		String vista = "auditories/auditoriasMatch";
		
		Iterable<Match> matches =  matchService.findAll();
		modelMap.addAttribute("matches", matches);
		return vista;
	}    @GetMapping(path="/achievements")
	public String auditoriasAchievement(ModelMap modelMap) {
		String vista = "auditories/auditoriasAchievement";
		
		Iterable<Achievement> achievements =  AchievementService.findAll();
		modelMap.addAttribute("achievements", achievements);
		return vista;
	}
}
