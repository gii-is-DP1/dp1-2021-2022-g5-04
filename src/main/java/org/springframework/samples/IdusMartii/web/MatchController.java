package org.springframework.samples.IdusMartii.web;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.samples.IdusMartii.service.MatchService;
import org.springframework.samples.IdusMartii.model.Match;


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
	// @PostMapping(path="/new")
	// public String guardarJugador(@Valid Player player, BindingResult result, ModelMap modelMap) {
	// 	String vista = "players/listadoJugadores";
	// 	if (result.hasErrors()) {
	// 		modelMap.addAttribute("players", player);
	// 		return "players/editarJugador";
	// 	} else {
	// 		playerService.savePlayer(player);
	// 		modelMap.addAttribute("message", "¡Jugador guardado correctamente!");
	// 	}
	// 	return vista;
	// }
	
}
