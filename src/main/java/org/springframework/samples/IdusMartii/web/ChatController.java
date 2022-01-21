package org.springframework.samples.IdusMartii.web;


import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.samples.IdusMartii.service.ChatService;
import org.springframework.samples.IdusMartii.service.CurrentUserService;
import org.springframework.samples.IdusMartii.service.MatchService;
import org.springframework.samples.IdusMartii.service.UserService;
import org.springframework.samples.IdusMartii.model.Chat;
import org.springframework.samples.IdusMartii.model.Match;
import org.springframework.samples.IdusMartii.model.User;

@Controller
@RequestMapping("/chats")
public class ChatController {
    
	
	@Autowired
	private ChatService chatService;
    @Autowired
	private MatchService matchService;
    @Autowired
	private UserService userService;
    @Autowired
    private CurrentUserService currentUserService;
		
	@GetMapping(path="/{id_match}")
	public String listadoChat(ModelMap modelMap, @PathVariable("id_match") int id_match, HttpServletResponse response) {
		String vista = "chats/chat";
        response.addHeader("Refresh","30");
        Match match = matchService.findById(id_match);
        User user = userService.findUser(currentUserService.showCurrentUser()).get();
        List<Chat> chats = chatService.findByMach(match);
        modelMap.addAttribute("msg", new Chat());
        modelMap.addAttribute("admin", matchService.isAdmin(user));
		modelMap.addAttribute("chats", chats);
        modelMap.addAttribute("match_id", match.getId());
        modelMap.addAttribute("currentUser", user);
		return vista;
	}
    @PostMapping(path="/{id_match}/save")
	public String saveChat(ModelMap modelMap, @PathVariable("id_match") int id_match, @Valid Chat chat) {
        Match match = matchService.findById(id_match);
        chat.setMatch(match);
        User user = userService.findUser(currentUserService.showCurrentUser()).get();
        chat.setUser(user);
        chatService.save(chat);
       
        return "redirect:/chats/" + match.getId();
		
	}
	
	
}

