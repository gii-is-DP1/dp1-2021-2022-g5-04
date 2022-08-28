package org.springframework.samples.idusmartii.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.idusmartii.model.Chat;
import org.springframework.samples.idusmartii.model.Match;
import org.springframework.samples.idusmartii.model.User;
import org.springframework.samples.idusmartii.repository.ChatRepository;

@Service
public class ChatService {
	
    @Autowired 
	private ChatRepository chatRepository;
	

	@Transactional
	public Iterable<Chat> findAll(){
		return chatRepository.findAll();
	}
    
	@Transactional(readOnly = true)
	public Chat findById(Integer id) throws DataAccessException{
		return chatRepository.findById(id).get();
	}
	
	@Transactional
	public void save(Chat chat) throws DataAccessException {
		chatRepository.save(chat);
	}
	@Transactional
	public List<Chat> findByMach(Match match) throws DataAccessException{
		List<Chat> chats = chatRepository.findByMatch(match);
		if(chats.size()>=6){
			chats = chats.subList(chats.size()-6, chats.size());

		}
		return chats;
	}
	@Transactional
	public void deleteChatsFromUser(User user) throws DataAccessException {
		List<Chat> chats = chatRepository.findByUser(user);
		for (Chat c: chats) {
			chatRepository.delete(c);
		}
	}
	@Transactional
	public Page<Chat> findChatWithPagination(Pageable pageable, Match match){
		return chatRepository.findChatWithPagination(match, pageable);
	}
	@Transactional
	public List<Integer> createNumberOfPagesList(Page<Chat> chatPage, int pageNumber){
		int numberOfPages = (chatPage.getNumberOfElements()/5) + 1;
		List<Integer> numberOfPagesList = new ArrayList<Integer>();
		int i = 1;
		while(i != numberOfPages + 1) {
			numberOfPagesList.add(i);
			i++;
		}
		numberOfPagesList.remove(numberOfPagesList.indexOf(pageNumber));
		return numberOfPagesList;
	}
   
    
}