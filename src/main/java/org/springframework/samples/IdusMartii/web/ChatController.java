package org.springframework.samples.IdusMartii.web;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		
	// @GetMapping(path="/{id_match}")
	// public String listadoChatss(ModelMap modelMap, @PathVariable("id_match") int id_match, HttpServletResponse response) {
	// 	String vista = "chats/chat";
    //     response.addHeader("Refresh","30");
    //     Match match = matchService.findById(id_match);
    //     User user = userService.findUser(currentUserService.showCurrentUser()).get();
    //     List<Chat> chats = chatService.findByMach(match);
    //     modelMap.addAttribute("msg", new Chat());
    //     modelMap.addAttribute("admin", matchService.isAdmin(user));
	// 	modelMap.addAttribute("chats", chats);
    //     modelMap.addAttribute("match_id", match.getId());
    //     modelMap.addAttribute("currentUser", user);
	// 	return vista;
	// }
    @GetMapping("/{id_match}")
	public String listadoChat(@RequestParam("page") int page, ModelMap modelMap, @PathVariable("id_match") int id_match, HttpServletResponse response,@PageableDefault(page = 0, size = 5)Pageable pageable){
    	//log.info("Accediendo al listado de usuarios...");
        Match match = matchService.findById(id_match);
		String vista = "chats/chat";
        response.addHeader("Refresh","30");
		List<Chat> chats =  chatService.findChatWithPagination(pageable, match);
        modelMap.addAttribute("pageNumber", pageable.getPageNumber());
		modelMap.addAttribute("pageSize", pageable.getPageSize());
		modelMap.addAttribute("hasPrevious", pageable.hasPrevious());
		modelMap.addAttribute("chats", chats);
        modelMap.addAttribute("id_match", id_match);
        modelMap.addAttribute("user", currentUserService.showCurrentUser());
        //modelMap.addAttribute("numberOfPagesList", chatService.createNumberOfPagesList(completeUserPage, page));
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

