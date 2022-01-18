package org.springframework.samples.IdusMartii.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.IdusMartii.enumerates.Plays;
import org.springframework.samples.IdusMartii.enumerates.Role;
import org.springframework.samples.IdusMartii.model.Achievement;
import org.springframework.samples.IdusMartii.model.Player;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.samples.IdusMartii.repository.AchievementUserRepository;
import org.springframework.samples.IdusMartii.repository.FriendsRepository;
import org.springframework.samples.IdusMartii.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendsService {
	@Autowired
	private FriendsRepository friendsRepository;
	
	@Transactional
	public void saveFriends(String username1, String username2) throws DataAccessException {
		friendsRepository.saveFriends1(username1, username2);
		friendsRepository.saveFriends2(username2, username1);
		
	}
	
}
