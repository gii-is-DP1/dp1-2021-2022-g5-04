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
	public String listadoPartida(ModelMap modelMap) {
		String vista = "matches/listadoPartida";
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
		String vista = "matches/listadoPartida";
	
			matchService.saveMatch(player);
			modelMap.addAttribute("message", "¡Jugador guardado correctamente!");
		
		return "redirect:/matches";
	}


	@GetMapping(path="/{id}/new")
	public String crearJugador1(ModelMap modelMap, @PathVariable("id") int id) {
		String vista = "matches/editarPartida";
		Match owner = this.matchService.findById(id);

		modelMap.addAttribute("match", owner);
		return vista;
	}
	
	@GetMapping(path="/{id}/save")
	public String guardarJugador2(  @PathVariable("id") int id, ModelMap modelMap) {
		String vista = "matches/listadoPartida";
	
		Match owner = this.matchService.findById(id);
		modelMap.addAttribute(owner);
		
		return vista;
	}
	@PostMapping(path="/{id}/save")
	public String guardarJugador(  ModelMap modelMap, @PathVariable("id") int id) {
	
			// String vista = "matches/listadoPartida";
 
		
				//match.setId(id);
			Match owner = this.matchService.findById(id);
			owner.setTurn(owner.getTurn()+1);
			owner.setVotoaFavor(owner.getVotoaFavor()+1);

			if (owner.getTurn() == 5) {
				owner.setTurn(0);
				owner.setRound(owner.getRound()+1);

			}
			
			if(owner.getRound() == 2) {
				owner.setTurn(0);
				owner.setRound(0);

			}
				this.matchService.saveMatch(owner);
				return "redirect:/matches/" + id + "/new" ;
			
		}
	
	
	@GetMapping(path="/{id}/saver")
	public String guardarJugador21(  @PathVariable("id") int id, ModelMap modelMap) {
		String vista = "matches/listadoPartida";
	
		Match owner = this.matchService.findById(id);
		modelMap.addAttribute(owner);
		
		return vista;
	}
	@PostMapping(path="/{id}/saver")
	public String guardarJugador1(  ModelMap modelMap, @PathVariable("id") int id) {
	
			// String vista = "matches/listadoPartida";
 
		
				//match.setId(id);
			Match owner = this.matchService.findById(id);
			owner.setTurn(owner.getTurn()+1);
			owner.setVotoenContra(owner.getVotoenContra()+1);

			if (owner.getTurn() == 5) {
				owner.setTurn(0);
				owner.setRound(owner.getRound()+1);

			}
			
			if(owner.getRound() == 2) {
				owner.setTurn(0);
				owner.setRound(0);

			}
				this.matchService.saveMatch(owner);
				return "redirect:/matches/" + id + "/new" ;
			
		}
	
	
	@GetMapping(path="/{id}/saven")
	public String guardarJugador212(  @PathVariable("id") int id, ModelMap modelMap) {
		String vista = "matches/listadoPartida";
	
		Match owner = this.matchService.findById(id);
		modelMap.addAttribute(owner);
		
		return vista;
	}
	@PostMapping(path="/{id}/saven")
	public String guardarJugador5(  ModelMap modelMap, @PathVariable("id") int id) {
	
			// String vista = "matches/listadoPartida";
 
		
				//match.setId(id);
			Match owner = this.matchService.findById(id);
			owner.setTurn(owner.getTurn()+1);

			if (owner.getTurn() == 5) {
				owner.setTurn(0);
				owner.setRound(owner.getRound()+1);

			}
			
			if(owner.getRound() == 2) {
				owner.setTurn(0);
				owner.setRound(0);

			}
				this.matchService.saveMatch(owner);
				return "redirect:/matches/" + id + "/new" ;
			
		}
	
	
	
	
	
}