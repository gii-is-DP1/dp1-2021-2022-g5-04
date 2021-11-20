package org.springframework.samples.IdusMartii.web;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.samples.IdusMartii.service.MatchService;
import org.springframework.samples.IdusMartii.model.Match;
import org.springframework.samples.IdusMartii.model.Player;


@Controller
@RequestMapping("/matches")
public class MatchController {
	
	@Autowired
	private MatchService matchService;
	
	@GetMapping()
	public String listadoPartidas(ModelMap modelMap) {
		String vista = "matches/listadoPartidas";
		Iterable<Match> matches = matchService.findAll();
		modelMap.addAttribute("matches", matches);
		return vista;
	}
	
	@GetMapping(path="/new")
	public String crearPartida(ModelMap modelMap) {
		String vista = "matches/crearPartida";
		modelMap.addAttribute("match", new Match());
		return vista;
	}
	@PostMapping(path="/save")
	public String guardarJugador1(@Valid Match player, BindingResult result, ModelMap modelMap) {
		String vista = "matches/listadoPartidas";
	
			matchService.saveMatch(player);
			modelMap.addAttribute("message", "¡Jugador guardado correctamente!");
		
		return "redirect:/matches";
	}


	@GetMapping(path="/{id}/new")
	public String crearJugador1(ModelMap modelMap, @PathVariable("id") int id) {
		String vista = "matches/editarPartidas";
		modelMap.addAttribute("match", new Match());
		return vista;
	}
	
	@GetMapping(path="/{id}/save")
	public String guardarJugador2( BindingResult result, @PathVariable("id") int id, ModelMap modelMap) {
		String vista = "matches/listadoJugadores";
	
		Match owner = this.matchService.findById(id);
		modelMap.addAttribute(owner);
		
		return vista;
	}
	@PostMapping(path="/{id}/save")
	public String guardarJugador(@Valid Match match, BindingResult result, ModelMap modelMap, @PathVariable("id") int id) {
	
			String vista = "players/listadoJugadores";

		
				match.setId(id);
				this.matchService.saveMatch(match);
				return "redirect:/matches";
			
		}
	
	
	
	
	
	
	
	
	
	
}
