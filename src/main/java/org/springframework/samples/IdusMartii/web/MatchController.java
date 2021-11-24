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
		// String a = currentUserService.showCurrentUser();
		// System.out.println(a);
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
	public String guardarJugador1(@Valid Match match, BindingResult result, ModelMap modelMap) {
			Player host = new Player();
			host.setMatch(match);
			host.setUser(userService.findUser(currentUserService.showCurrentUser()).get());
			host.setName("host");
			matchService.saveMatch(match);
			playerService.savePlayer(host);
			currentUserService.showCurrentUser();
			modelMap.addAttribute("message", "¡Jugador guardado correctamente!");
			return "redirect:/matches/" + match.getId() + "/new";
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
				if(owner.getVotoaFavor()==((owner.getVotoenContra()-1) )) {
					return "matches/victoriaF" ;}
				else if(owner.getVotoaFavor()==((owner.getVotoenContra()-2) )) {
					return "matches/victoriaF" ;}
				else if(owner.getVotoaFavor()-1==((owner.getVotoenContra()) )) {
					return "matches/victoriaC" ;}	
				else if(owner.getVotoaFavor()-2==((owner.getVotoenContra()) )) {
						return "matches/victoriaC" ;}
				else {
					return "matches/victoriaM" ;
				}

			}
			if(owner.getVotoaFavor()==5) {
				return "matches/victoriaF" ;

			}else if(owner.getVotoenContra()==5) {
				return "matches/victoriaC" ;

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
				if(owner.getVotoaFavor()==((owner.getVotoenContra()-1) )) {
					return "matches/victoriaF" ;}
				else if(owner.getVotoaFavor()==((owner.getVotoenContra()-2) )) {
					return "matches/victoriaF" ;}
				else if(owner.getVotoaFavor()-1==((owner.getVotoenContra()) )) {
					return "matches/victoriaC" ;}	
				else if(owner.getVotoaFavor()-2==((owner.getVotoenContra()) )) {
						return "matches/victoriaC" ;}
				else {
					return "matches/victoriaM" ;
				}

			}
			if(owner.getVotoaFavor()==5) {
				return "matches/victoriaF" ;

			}else if(owner.getVotoenContra()==5) {
				return "matches/victoriaC" ;

			}
				this.matchService.saveMatch(owner);
				return "redirect:/matches/" + id + "/new" ;
			
		
	
		}
	@GetMapping(path="/{id}/saven")
	public String guardarJugador2w21(  @PathVariable("id") int id, ModelMap modelMap) {
		String vista = "matches/listadoPartida";
	
		Match owner = this.matchService.findById(id);
		modelMap.addAttribute(owner);
		
		return vista;
	}
	@PostMapping(path="/{id}/saven")
	public String guardarJugador12(  ModelMap modelMap, @PathVariable("id") int id) {
	
			// String vista = "matches/listadoPartida";
 
		
				//match.setId(id);
			Match owner = this.matchService.findById(id);
			owner.setTurn(owner.getTurn()+1);

			if (owner.getTurn() == 5) {
				owner.setTurn(0);
				owner.setRound(owner.getRound()+1);

			}
			
			if(owner.getRound() == 2) {
				if(owner.getVotoaFavor()==((owner.getVotoenContra()-1) )) {
					return "matches/victoriaF" ;}
				else if(owner.getVotoaFavor()==((owner.getVotoenContra()-2) )) {
					return "matches/victoriaF" ;}
				else if(owner.getVotoaFavor()-1==((owner.getVotoenContra()) )) {
					return "matches/victoriaC" ;}	
				else if(owner.getVotoaFavor()-2==((owner.getVotoenContra()) )) {
						return "matches/victoriaC" ;}
				else {
					return "matches/victoriaM" ;
				}

			}
			if(owner.getVotoaFavor()==5) {
				return "matches/victoriaF" ;

			}else if(owner.getVotoenContra()==5) {
				return "matches/victoriaC" ;

			}
				this.matchService.saveMatch(owner);
				return "redirect:/matches/" + id + "/new" ;
			
		}
	
	
	
	
	
	
}