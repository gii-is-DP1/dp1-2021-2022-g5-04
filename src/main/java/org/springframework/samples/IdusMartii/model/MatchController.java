package org.springframework.samples.IdusMartii.model;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import  org.springframework.samples.IdusMartii.model.Player;
import  org.springframework.samples.IdusMartii.model.PlayerService;
import  org.springframework.samples.IdusMartii.model.Match;
import  org.springframework.samples.IdusMartii.model.MatchesService;
import org.springframework.samples.IdusMartii.owner.Owner;


@Controller
@RequestMapping("/matches")
public class MatchController {
	@Autowired
	private PlayerService palyerService;
	private MatchesService matchesService;

	

	@GetMapping(value = "/{id}")
	public String listadoJugadores(ModelMap modelMap, @PathVariable("id") int id) {
		String vista = "matches/listadoPartidas";
		Iterable<Player> players = palyerService.findAll();
		
		//Iterable<Match> g = matchesService.findAll();
		
		modelMap.addAttribute("players", players);
		modelMap.addAttribute("match", id);
		//modelMap.addAttribute("matches", g);


		return vista;
	}
	
}
