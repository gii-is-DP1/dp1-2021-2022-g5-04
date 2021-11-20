package org.springframework.samples.IdusMartii.model;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import  org.springframework.samples.IdusMartii.model.Player;
import  org.springframework.samples.IdusMartii.model.PlayerService;
import  org.springframework.samples.IdusMartii.model.Match;
import  org.springframework.samples.IdusMartii.model.MatchesService;
import org.springframework.samples.IdusMartii.owner.Owner;
import org.springframework.samples.IdusMartii.pet.Pet;


@Controller
@RequestMapping("/matches")
public class MatchController {
	@Autowired
	private MatchesService matchesService;

	@GetMapping()
	public String j(ModelMap modelMap) {
		String vista = "matches/listadoPartidas2";
		Iterable<Match> players = matchesService.findAll();
	
		modelMap.addAttribute("players", players);


		return  vista;
	}
	@GetMapping(path="/{id}")
	public String crearJugador(ModelMap modelMap, @PathVariable("id") Integer id) {
		String vista = "matches/verPartida";
		
		Iterable<Match> players = matchesService.findAll();
		
		modelMap.addAttribute("players", players);
	modelMap.addAttribute("id",id);
		

		return vista;
	}
	
	@GetMapping(value = "/{id}/new")
	public String crearJugador2(ModelMap modelMap , @PathVariable("id") String id) {
		String vista = "matches/editarJugador";
		
		modelMap.addAttribute("player",  matchesService.getM(String.valueOf(id)));
		return vista;
	}
	
	
	//@PostMapping(value = "/{id}/save")
	//public String guardarJugador(@RequestBody Match match, BindingResult result, ModelMap modelMap, @PathVariable("id") String id) {
		
		//Object p = modelMap.getAttribute("match");
		//String vista = "matches/verPartida";
		
			
		//matchesService.update(id, match);
		//	modelMap.addAttribute("message", "¡Jugador guardado correctamente!" + p.toString());
		
		//return vista;
	//}
	@GetMapping(value = "/{id}/save")
	public String initUpdateOwnerForm(@PathVariable("id") int id, Model model) {
		String vista = "matches/verPartida";

		Match match = this.matchesService.getM(String.valueOf(id));
		model.addAttribute(match);
		return vista;
	}

	@PostMapping(value = "/{id}/save")
	public String processUpdateOwnerForm(@Valid Match match, BindingResult result,
			@PathVariable("id") int id) {
		String vista = "matches/verPartida";

		
			match.setId(id);
			this.matchesService.saveMatch(match);
			return "redirect:/matches/{ownerId}";
		
	}
	
	
}


