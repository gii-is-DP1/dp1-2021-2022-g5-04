package org.springframework.samples.IdusMartii.model;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.IdusMartii.owner.Owner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/players2")
public class PlayerController2 {
	
	@Autowired
	private PlayerService2 playerService;
	
	@GetMapping()
	public String listadoJugadores(ModelMap modelMap) {
		String vista = "players/listadoJugadores";
		Iterable<Player2> players = playerService.findAll();
		modelMap.addAttribute("players", players);
		return vista;
	}
	
	
	@GetMapping(path="/new")
	public String crearJugador(ModelMap modelMap) {
		String vista = "players2/editarJugador";
		modelMap.addAttribute("player", new Player());
		return vista;
	}
	
	@GetMapping(path="/{id}/new")
	public String crearJugador1(ModelMap modelMap, @PathVariable("id") int id) {
		String vista = "players/editarJugador";
		modelMap.addAttribute("player", new Player());
		return vista;
	}
	
	@GetMapping(path="/{id}/save")
	public String guardarJugador2( BindingResult result, @PathVariable("id") int id, ModelMap modelMap) {
		String vista = "players/listadoJugadores";
	
		Player2 owner = this.playerService.getPlayer(id);
		modelMap.addAttribute(owner);
		
		return vista;
	}
	@PostMapping(path="/{id}/save")
	public String guardarJugador(@Valid Player2 player, BindingResult result, ModelMap modelMap, @PathVariable("id") int id) {
	
			String vista = "players/listadoJugadores";

		
				player.setId(id);
				this.playerService.savePlayer(player);
				return "redirect:/players2";
			
		}
	@PostMapping(path="/save")
	public String guardarJugador1(@Valid Player2 player, BindingResult result, ModelMap modelMap) {
		String vista = "players/listadoJugadores";
	
			playerService.savePlayer(player);
			modelMap.addAttribute("message", "¡Jugador guardado correctamente!");
		
		return vista;
	}
}
