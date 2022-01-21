package org.springframework.samples.IdusMartii.service;

import org.springframework.samples.IdusMartii.repository.ChatRepository;

import org.springframework.samples.IdusMartii.model.Chat;
import org.springframework.samples.IdusMartii.model.Match;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;

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
	
   
    
}