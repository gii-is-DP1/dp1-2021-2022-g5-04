package org.springframework.samples.IdusMartii.web;



import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

import org.springframework.samples.IdusMartii.service.AuthoritiesService;
import org.springframework.samples.IdusMartii.service.CurrentUserService;
import org.springframework.samples.IdusMartii.service.MatchService;
import org.springframework.samples.IdusMartii.service.InvitationService;
import org.springframework.samples.IdusMartii.service.UserService;
import org.springframework.samples.IdusMartii.model.Invitation;
import org.springframework.samples.IdusMartii.model.Match;
import org.springframework.samples.IdusMartii.model.User;

@Slf4j
@Controller
@RequestMapping("/invitations")
public class InvitationController {
    
	
	@Autowired
	private InvitationService invitationService;
	@Autowired
	private CurrentUserService currentUserService;
	@Autowired
	private UserService userService;
	@Autowired
	private MatchService matchService;
	@Autowired
	private AuthoritiesService authoritiesService;
	
		
	@GetMapping()
	public String listadoInvitaciones(ModelMap modelMap) {
		log.info("Solicitando la lista de Invitaciones a partida...");
		String vista = "invitations/listadoInvitaciones";
		log.info("Accediendo al servicio de Usuario por el método findUser()");
        User user = userService.findUser(currentUserService.showCurrentUser()).get();
        log.info("Accediendo al servicio de invitaciones a partida por el metodo findByUser");
		List<Invitation> invitations = invitationService.findByUser(user);
		modelMap.addAttribute("invitations", invitations);
		modelMap.addAttribute("admin", authoritiesService.getAuthorities(user.getUsername()));
		return vista;
	}
	@PostMapping(path="/{id_invt}/rechazar")
	public String rechazarPartida(ModelMap modelMap, @PathVariable("id_invt") int id_invt) {
		log.info("Rechazando invitacion a partida...");
		log.info("Accediendo al servicio de invitaciones de partida por el metodo findById()");
		log.debug("Id = " + id_invt);
			Invitation invitation = invitationService.findById(id_invt);
		log.info("Accediendo al servicio de invitaciones de partida por el metodo deleteInvitation()");
			invitationService.deleteInvitation(invitation);
			return "redirect:/invitations";
	}
	@PostMapping(path="/{id_match}/save")
	public String guardarInvitacion(@Valid User user, BindingResult result, ModelMap modelMap, @PathVariable("id_match") int id_match) {
		log.info("Creando invitación de partida");
		String username = user.getUsername();
		log.info("Accediendo al servicio  de usuarios por el metodo findUser");
		log.debug("username del usuario: " + username);
		User usuario = userService.findUser(username).get();
		log.info("Accediendo al servicio de partidas por el metodo findById()");
		log.debug("id de partida: " + id_match);
		Match match = matchService.findById(id_match);
		
		log.info("Accediendo al servicio de partidas por el metodo matchContainUser() y al servicio de invitaciones de partidas por el metodo findByUserAndMatch()");
		log.debug("partida: " + match + ", usuario: " + usuario);
		if(matchService.matchContainUser(match, usuario) && (invitationService.findByUserAndMatch(usuario, match).size()!=0)){
			modelMap.addAttribute("message", "Este usuario ya está en la partida");
		}
		else{
			Invitation invitation = new Invitation();
			Date fecha = new Date();
			invitation.setUser(usuario);
			invitation.setMatch(match);	
			invitation.setFecha(fecha);
			log.info("Accediendo al servicio de invitaciones de partida por el metodo saveInivtation()");
			log.debug("Invitacion = " + invitation);
			invitationService.saveInvitation(invitation);
		}
		return "redirect:/matches/" + match.getId() + "/new";
	}
	
}
