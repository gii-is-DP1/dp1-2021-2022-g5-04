package org.springframework.samples.IdusMartii.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.samples.IdusMartii.repository.FriendInvitationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FriendsService {
	@Autowired
	private FriendInvitationRepository friendsRepository;
	@Autowired
	private UserService userService;
	
	@Transactional(rollbackFor = Exception.class)
	public void saveFriends(String username1, String username2) throws DataAccessException {
    log.info("Llamada al metodo saveFriends(String, String)");
    log.debug("atributos: " + username1 + ", " + username2);
		if (username1 != username2) {
			friendsRepository.saveFriends1(username1, username2);
			friendsRepository.saveFriends2(username2, username1);
		} else {
			throw new DataAccessException("Un usuario no puede agregarse a si mismo como amigo") {};
		}
	}
	
	@Transactional
	public void deleteAllFriendsFromUser(User user) throws DataAccessException {
		List<String> friendsFromUser = friendsRepository.findUserFriendsFromUsername(user.getUsername());
		for (String s: friendsFromUser) {
			userService.deleteFriend(user, s);
		}
	}
}
