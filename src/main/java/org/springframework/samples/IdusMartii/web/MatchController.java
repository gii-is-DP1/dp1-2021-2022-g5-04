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
import org.springframework.samples.IdusMartii.service.UserService;
import org.springframework.samples.IdusMartii.service.PlayerService;
import org.springframework.samples.IdusMartii.service.CurrentUserService;
import org.springframework.samples.IdusMartii.enumerates.Role;
import org.springframework.samples.IdusMartii.model.Match;
import org.springframework.samples.IdusMartii.model.Player;
import org.springframework.samples.IdusMartii.repository.PlayerRepository;

@Controller
@RequestMapping("/matches")
public class MatchController {
	
	@Autowired
	private MatchService matchService;
	@Autowired
	private CurrentUserService currentUserService;
	@Autowired
	private UserService userService;
	@Autowired
	private PlayerService playerService;
	
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
	public String guardarPartida(@Valid Match match, BindingResult result, ModelMap modelMap) {
			Player host = new Player();
			host.setMatch(match);
			host.setUser(userService.findUser(currentUserService.showCurrentUser()).get());
			host.setName("host");
			host.setRole(Role.CONSUL);
			matchService.saveMatch(match);
			playerService.savePlayer(host);
			modelMap.addAttribute("message", "¡Jugador guardado correctamente!");
			return "redirect:/matches/" + match.getId() + "/new";
	}


	@GetMapping(path="/{id}/new")
	public String editarPartida(ModelMap modelMap, @PathVariable("id") int id) {
		String vista = "matches/editarPartida";
		Match match = this.matchService.findById(id);
		String currentuser = currentUserService.showCurrentUser();
		modelMap.addAttribute("current", currentuser);
		modelMap.addAttribute("match", match);
		return vista;
	}
	
	@GetMapping(path="/{id}/save")
	public String guardarJugador2(  @PathVariable("id") int id, ModelMap modelMap) {
		String vista = "matches/listadoPartida";
	
		Match match = this.matchService.findById(id);
		modelMap.addAttribute(match);
		
		return vista;
	}
	@PostMapping(path="/{id}/save")
	public String guardarJugador(  ModelMap modelMap, @PathVariable("id") int id) {
	
			// String vista = "matches/listadoPartida";
 
		
				//match.setId(id);
			Match match = this.matchService.findById(id);
			match.setTurn(match.getTurn()+1);
			match.setVotoaFavor(match.getVotoaFavor()+1);

			if (match.getTurn() == 5) {
				match.setTurn(0);
				match.setRound(match.getRound()+1);

			}
			if(match.getRound() == 2) {
				if(match.getVotoaFavor()==((match.getVotoenContra()-1) )) {
					return "matches/victoriaF" ;}
				else if(match.getVotoaFavor()==((match.getVotoenContra()-2) )) {
					return "matches/victoriaF" ;}
				else if(match.getVotoaFavor()-1==((match.getVotoenContra()) )) {
					return "matches/victoriaC" ;}	
				else if(match.getVotoaFavor()-2==((match.getVotoenContra()) )) {
						return "matches/victoriaC" ;}
				else {
					return "matches/victoriaM" ;
				}

			}
			if(match.getVotoaFavor()==5) {
				return "matches/victoriaF" ;

			}else if(match.getVotoenContra()==5) {
				return "matches/victoriaC" ;

			}
				this.matchService.saveMatch(match);
				return "redirect:/matches/" + id + "/new" ;
			
		
	
		}
	
	
	@GetMapping(path="/{id}/saver")
	public String guardarJugador21(  @PathVariable("id") int id, ModelMap modelMap) {
		String vista = "matches/listadoPartida";
	
		Match match = this.matchService.findById(id);
		modelMap.addAttribute(match);
		
		return vista;
	}
	@PostMapping(path="/{id}/saver")
	public String guardarJugador1(  ModelMap modelMap, @PathVariable("id") int id) {
	
			// String vista = "matches/listadoPartida";
 
		
				//match.setId(id);
			Match match = this.matchService.findById(id);
			match.setTurn(match.getTurn()+1);
			match.setVotoenContra(match.getVotoenContra()+1);

			if (match.getTurn() == 5) {
				match.setTurn(0);
				match.setRound(match.getRound()+1);

			}
			
			if(match.getRound() == 2) {
				if(match.getVotoaFavor()==((match.getVotoenContra()-1) )) {
					return "matches/victoriaF" ;}
				else if(match.getVotoaFavor()==((match.getVotoenContra()-2) )) {
					return "matches/victoriaF" ;}
				else if(match.getVotoaFavor()-1==((match.getVotoenContra()) )) {
					return "matches/victoriaC" ;}	
				else if(match.getVotoaFavor()-2==((match.getVotoenContra()) )) {
						return "matches/victoriaC" ;}
				else {
					return "matches/victoriaM" ;
				}

			}
			if(match.getVotoaFavor()==5) {
				return "matches/victoriaF" ;

			}else if(match.getVotoenContra()==5) {
				return "matches/victoriaC" ;

			}
				this.matchService.saveMatch(match);
				return "redirect:/matches/" + id + "/new" ;
			
		
	
		}
	@GetMapping(path="/{id}/saven")
	public String guardarJugador2w21(  @PathVariable("id") int id, ModelMap modelMap) {
		String vista = "matches/listadoPartida";
	
		Match match = this.matchService.findById(id);
		modelMap.addAttribute(match);
		
		return vista;
	}
	@PostMapping(path="/{id}/saven")
	public String guardarJugador12(  ModelMap modelMap, @PathVariable("id") int id) {
	
			// String vista = "matches/listadoPartida";
 
		
				//match.setId(id);
			Match match = this.matchService.findById(id);
			match.setTurn(match.getTurn()+1);

			if (match.getTurn() == 5) {
				match.setTurn(0);
				match.setRound(match.getRound()+1);

			}
			
			if(match.getRound() == 2) {
				if(match.getVotoaFavor()==((match.getVotoenContra()-1) )) {
					return "matches/victoriaF" ;}
				else if(match.getVotoaFavor()==((match.getVotoenContra()-2) )) {
					return "matches/victoriaF" ;}
				else if(match.getVotoaFavor()-1==((match.getVotoenContra()) )) {
					return "matches/victoriaC" ;}	
				else if(match.getVotoaFavor()-2==((match.getVotoenContra()) )) {
						return "matches/victoriaC" ;}
				else {
					return "matches/victoriaM" ;
				}

			}
			if(match.getVotoaFavor()==5) {
				return "matches/victoriaF" ;

			}else if(match.getVotoenContra()==5) {
				return "matches/victoriaC" ;

			}
				this.matchService.saveMatch(match);
				return "redirect:/matches/" + id + "/new" ;
			
		}
		@GetMapping(path="/{id}/game")
		public String empezarPartida(ModelMap modelMap, @PathVariable("id") int id) {
		String vista = "matches/listadoPartida";
		Match match = this.matchService.findById(id);

		modelMap.addAttribute("match", match);
		return vista;
	}
	@PostMapping(path="/{id}/game/save")
	public String guardarPartidaEmpezada(  ModelMap modelMap, @PathVariable("id") int id) {
	
			// String vista = "matches/listadoPartida";
 
		
				//match.setId(id);
			// Match match = this.matchService.findById(id);
			// match.setTurn(match.getTurn()+1);
			// match.setVotoaFavor(match.getVotoaFavor()+1);

			// if (match.getTurn() == 5) {
			// 	match.setTurn(0);
			// 	match.setRound(match.getRound()+1);

			// }
			// if(match.getRound() == 2) {
			// 	if(match.getVotoaFavor()==((match.getVotoenContra()-1) )) {
			// 		return "matches/victoriaF" ;}
			// 	else if(match.getVotoaFavor()==((match.getVotoenContra()-2) )) {
			// 		return "matches/victoriaF" ;}
			// 	else if(match.getVotoaFavor()-1==((match.getVotoenContra()) )) {
			// 		return "matches/victoriaC" ;}	
			// 	else if(match.getVotoaFavor()-2==((match.getVotoenContra()) )) {
			// 			return "matches/victoriaC" ;}
			// 	else {
			// 		return "matches/victoriaM" ;
			// 	}

			// }
			// if(match.getVotoaFavor()==5) {
			// 	return "matches/victoriaF" ;

			// }else if(match.getVotoenContra()==5) {
			// 	return "matches/victoriaC" ;

			// }
			// 	this.matchService.saveMatch(match);
				return "redirect:/matches" ;
			
		
	
		}
	
	
	
	
	
	
}