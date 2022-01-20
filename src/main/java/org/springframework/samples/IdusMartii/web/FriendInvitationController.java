package org.springframework.samples.IdusMartii.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.IdusMartii.model.FriendInvitation;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.samples.IdusMartii.service.AuthoritiesService;
import org.springframework.samples.IdusMartii.service.CurrentUserService;
import org.springframework.samples.IdusMartii.service.FriendInvitationService;
import org.springframework.samples.IdusMartii.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/friendInvitations")
public class FriendInvitationController {
	
	@Autowired
	FriendInvitationService friendInvitationService;
	@Autowired
	private CurrentUserService currentUserService;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthoritiesService authoritiesService;
	
		
	@GetMapping()
	public String listadoFriendInvitaciones(ModelMap modelMap) {
		log.info("Solicitando lista de Solicitudes de Amistad...");
		String vista = "friendInvitations/listadoFriendInvitaciones";
        User user = userService.findUser(currentUserService.showCurrentUser()).get();
        log.info("Accediendo al servicio de solicitudes de amistad por el metodo findFriendInvitationsByUserRequested()");
		List<FriendInvitation> friendInvitations = friendInvitationService.findFriendInvitationsByUserRequested(user);
		if(friendInvitations.isEmpty()) {
			log.info("Nadie quiere ser tu amigo todavía pero ¡no te rindas! ^.^");
		}
		modelMap.addAttribute("friendInvitations", friendInvitations);
		modelMap.addAttribute("admin", authoritiesService.getAuthorities(user.getUsername()));
		return vista;
	}
	@PostMapping(path="/{id_invt}/decline")
	public String rechazarInvitacion(ModelMap modelMap, @PathVariable("id_invt") int id_invt) {
		log.info("Rechazando invitacion...");
		log.info("Accediendo al servicio de solicitudes de amistad por el metodo findById()");
			FriendInvitation friendInvitation = friendInvitationService.findById(id_invt);
			friendInvitationService.deleteFriendInvitation(friendInvitation);
			return "redirect:/friendInvitations";
	}
	@PostMapping(path="/{userRequester}/{userRequested}/save")
	public String guardarInvitacion(ModelMap modelMap, @PathVariable("userRequester") User userRequester, @PathVariable("userRequested") User userRequested) {
		log.info("Creando solicitud de amistad...");
		log.debug("Usuario1: " + userRequester + ", Usuario2: " + userRequested);
		FriendInvitation friendInvitation = new FriendInvitation();
		Date fecha = new Date();
		friendInvitation.setFecha(fecha);
		friendInvitation.setUser_requested(userRequested);
		friendInvitation.setUser_requester(userRequester);
		log.info("Accediendo al servicio de solicitudes de amistad por el metodo saveFriendInvitation()");
		friendInvitationService.saveFriendInvitation(friendInvitation);
		return "redirect:/friendInvitations";
	}
	
	@PostMapping(path="/{id_invt}/accept")
	public String aceptarInvitacion(ModelMap modelMap, @PathVariable("id_invt") int id_invt) {
		log.info("Aceptando Solicitud de amistad...");
		log.debug("Id de solicitud: " + id_invt);
		log.info("Accediendo al servicio de solicitudes de amistad por el método acceptFriendInvitatoin()");
			friendInvitationService.acceptFriendInvitation(id_invt);
			return "redirect:/friendInvitations";
	}
	
}
