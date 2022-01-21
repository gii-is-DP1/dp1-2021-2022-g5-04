package org.springframework.samples.IdusMartii.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.IdusMartii.model.FriendInvitation;
import org.springframework.samples.IdusMartii.model.Person;
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
		String vista = "friendInvitations/listadoFriendInvitaciones";
        User user = userService.findUser(currentUserService.showCurrentUser()).get();
		List<FriendInvitation> friendInvitations = friendInvitationService.findFriendInvitationsByUserRequested(user);
		modelMap.addAttribute("friendInvitations", friendInvitations);
		modelMap.addAttribute("admin", authoritiesService.getAuthorities(user.getUsername()));
		return vista;
	}
	@PostMapping(path="/{id_invt}/decline")
	public String rechazarInvitacion(ModelMap modelMap, @PathVariable("id_invt") int id_invt) {
			FriendInvitation friendInvitation = friendInvitationService.findById(id_invt);
			friendInvitationService.deleteFriendInvitation(friendInvitation);
			return "redirect:/friendInvitations";
	}
	@GetMapping(path="/{usernameRequester}/{usernameRequested}/save")
	public String guardarInvitacion(ModelMap modelMap, @PathVariable("usernameRequester") String usernameRequester, @PathVariable("usernameRequested") String usernameRequested) {
		User userRequester = userService.findbyUsername(usernameRequester);
		User userRequested = userService.findbyUsername(usernameRequested);
		if (friendInvitationService.letFriendRequest(userRequester, userRequested)) {
			FriendInvitation friendInvitation = new FriendInvitation();
			Date fecha = new Date();
			friendInvitation.setFecha(fecha);
			friendInvitation.setUser_requested(userRequested);
			friendInvitation.setUser_requester(userRequester);
			friendInvitationService.saveFriendInvitation(friendInvitation);
			modelMap.addAttribute("message", "Petición enviada con éxito al usuario con nombre " + userRequester.getUsername());
			modelMap.addAttribute("admin", authoritiesService.getAuthorities(userRequester.getUsername()));
	        modelMap.put("people", userService.crearAlumnos());
	        modelMap.put("title", "Idus Martii"); 
	        modelMap.put("group", "L5-4");
			return "/welcome";
		} else {
			modelMap.addAttribute("message", "Ya eres amigo de ese usuario");
			modelMap.addAttribute("admin", authoritiesService.getAuthorities(userRequester.getUsername()));
			return "/exception";
		}
		
	}
	
	@PostMapping(path="/{id_invt}/accept")
	public String aceptarInvitacion(ModelMap modelMap, @PathVariable("id_invt") int id_invt) {
			friendInvitationService.acceptFriendInvitation(id_invt);
			return "redirect:/friendInvitations";
	}
	
	

}
