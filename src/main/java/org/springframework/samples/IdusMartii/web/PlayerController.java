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
import org.springframework.samples.IdusMartii.service.CurrentUserService;
import org.springframework.samples.IdusMartii.service.MatchService;
import org.springframework.samples.IdusMartii.service.PlayerService;
import org.springframework.samples.IdusMartii.service.UserService;
import org.springframework.samples.IdusMartii.enumerates.Vote;
import org.springframework.samples.IdusMartii.model.Match;
import org.springframework.samples.IdusMartii.model.Player;

@Controller
@RequestMapping("/players")
public class PlayerController {
	
	@Autowired
	private PlayerService playerService;
	@Autowired
	private CurrentUserService currentUserService;
	@Autowired
	private UserService userService;

	@Autowired
	private MatchService matchesService;

		
	@GetMapping()
	public String listadoJugadores(ModelMap modelMap) {
		String vista = "players/listadoJugadores";
		Iterable<Player> players = playerService.findAll();
		modelMap.addAttribute("players", players);
		return vista;
	}
	
	@GetMapping(path="/new")
	public String crearJugador(ModelMap modelMap) {
		String vista = "players/editarJugador";
		modelMap.addAttribute("player", new Player());
		return vista;
	}
	@PostMapping(path="/new")
	public String guardarJugador(@Valid Player player, BindingResult result, ModelMap modelMap) {
		String vista = "players/listadoJugadores";
		if (result.hasErrors()) {
			modelMap.addAttribute("players", player);
			return "players/editarJugador";
		} else {
			playerService.savePlayer(player);
			modelMap.addAttribute("message", "¡Jugador guardado correctamente!");
		}
		return vista;
	}
	


	@PostMapping(path="/{id}/{idMatch}/guardarVotoEnContra")
	public String votoRed(  ModelMap modelMap, @PathVariable("id") int id, @PathVariable("idMatch") int idMatch) {
	
			// String vista = "matches/listadoPartida";
 
		
				//match.setId(id);
		
			Player player = playerService.findbyId(id);
			
				player.setVote(Vote.RED);
				playerService.savePlayer(player);

			return "redirect:/matches/" + idMatch + "/match";}

	
	
	
@PostMapping(path="/{id}/{idMatch}/guardarVotoNulo")
public String votoYellow(  ModelMap modelMap, @PathVariable("id") int id, @PathVariable("idMatch") int idMatch) {

		// String vista = "matches/listadoPartida";

	
			//match.setId(id);
	
		Player player = playerService.findbyId(id);
		player.setVote(Vote.YELLOW);
		playerService.savePlayer(player);

		return "redirect:/matches/" + idMatch + "/match";}



@PostMapping(path="/{id}/{idMatch}/guardarVotoAFavor")
public String votoGreen(  ModelMap modelMap, @PathVariable("id") int id,@PathVariable("idMatch") int idMatch) {

		// String vista = "matches/listadoPartida";

	
			//match.setId(id);
	
		Player player = playerService.findbyId(id);
		player.setVote(Vote.GREEN);
		playerService.savePlayer(player);
		return "redirect:/matches/" + idMatch + "/match";}


}


